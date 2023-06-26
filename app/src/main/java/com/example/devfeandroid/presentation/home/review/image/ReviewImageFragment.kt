package com.example.devfeandroid.presentation.home.review.image

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.devfeandroid.R
import com.example.devfeandroid.data.model.producthome.review.CommentProduct
import com.example.devfeandroid.databinding.ReviewImageFragmentBinding
import com.example.devfeandroid.extensions.STRING_DEFAULT
import com.example.devfeandroid.extensions.disable
import com.example.devfeandroid.extensions.getAppString
import com.example.devfeandroid.extensions.gone
import com.example.devfeandroid.extensions.loadImageUrl
import com.example.devfeandroid.extensions.setOnSafeClick
import com.example.devfeandroid.presentation.BaseFragment
import com.example.devfeandroid.presentation.MainActivity
import com.example.devfeandroid.presentation.home.review.generic.CommentReviewDisplay
import com.example.devfeandroid.presentation.widget.recyclerview.scroll.BaseLoadMoreRecyclerView
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ReviewImageFragment : BaseFragment() {

    companion object {
        const val REVIEW_IMAGE_PRODUCT_KEY = "REVIEW_IMAGE_PRODUCT_KEY"
    }

    private val viewModel by lazy {
        ViewModelProvider(this)[ReviewImageViewModel::class.java]
    }

    private var binding: ReviewImageFragmentBinding? = null

    private val adapter = ReviewImageAdapter()

    private var baseLoadMoreRecyclerView: BaseLoadMoreRecyclerView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = ReviewImageFragmentBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity!!.showBottomNav(false)
        setEventView()
        setUpAdapter()
        observable()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        removeListener()
    }

    private fun setEventView() {

        mainViewModel.getStatusFollow(viewModel.product?.getUserInfo()?.getUserId()
            ?: STRING_DEFAULT)

        binding!!.srlReviewImage.setOnRefreshListener {
            viewModel.getInfoReviewProduct(true)
        }

        binding!!.ivReviewImageBack.setOnSafeClick {
            onBackPressFragment()
        }

        binding!!.ivReviewImageAvatarUser.loadImageUrl(viewModel.product?.getAvatarUserInfo()
            ?: STRING_DEFAULT)

        binding!!.fvReviewImageUser.setFollow { follow ->
            if (follow) {
                mainViewModel.follow(viewModel.product?.userInfo?.getUserId() ?: STRING_DEFAULT)
            } else {
                mainViewModel.unFollow(viewModel.product?.userInfo?.getUserId() ?: STRING_DEFAULT)
            }
        }
    }

    private fun setUpAdapter() {
        binding!!.rvReviewImage.adapter = this.adapter
        binding!!.rvReviewImage.layoutManager = LinearLayoutManager(context)

        baseLoadMoreRecyclerView = object : BaseLoadMoreRecyclerView(binding!!.rvReviewImage.layoutManager!!) {
            override fun onLoadMore() {
                viewModel.getListReview(true)
                baseLoadMoreRecyclerView!!.isLoading = true
            }
        }

        baseLoadMoreRecyclerView?.let {
            binding!!.rvReviewImage.addOnScrollListener(it)
        }

        addListener()
    }

    private fun addListener() {
        adapter.listener = object : IReviewImageListener {
            override fun onSeeReplyComment(commentID: String) {
                viewModel.getListChildReview(commentID)
            }

            override fun onRemoveReplyComment(commentID: String) {
                viewModel.removeListChildReview(commentID)
            }

            override fun onSeeMoreReplyComment(commentID: String, position: Int) {
                viewModel.getListChildReview(commentID,true)
            }

            override fun onLikeHeart(commentID: String) {
                viewModel.interactReview(commentID)
            }

            override fun onReplyComment(commentProduct: CommentProduct) {
            }
        }
    }

    private fun removeListener() {
        adapter.listener = null
    }

    private fun observable() {

        lifecycleScope.launchWhenCreated {
            mainViewModel.followState.collect {
                binding!!.fvReviewImageUser.isFollow(it)
            }
        }

        viewModel.reviewState.onEach {
            mainActivity!!.setState(it, object : MainActivity.IUIState<List<Any>> {

                override fun onError(s: String, exception: Throwable) {
                    super.onError(s, exception)
                    Log.e("tunglv", "onError: ${exception.toString()}")
                }

                override fun onSuccess(data: List<Any>) {
                    lifecycleScope.launch {
                        val list = data.map { src ->
                            if (src is CommentProduct) {
                                val commentReviewDisplay = CommentReviewDisplay(src, true)

                                if (viewModel.oldStateSeeReplyCommentReview.containsKey(src.getIdComment())) {
                                    commentReviewDisplay.isSeeReplyComment = !viewModel.oldStateSeeReplyCommentReview[src.getIdComment()]!!
                                    commentReviewDisplay
                                } else {
                                    commentReviewDisplay
                                }

                            } else {
                                src
                            }
                        }
                        adapter.submitList(list)
                        baseLoadMoreRecyclerView!!.isLoading = false
                        baseLoadMoreRecyclerView!!.lastPage = viewModel.page > 7

                        binding!!.srlReviewImage.isRefreshing = false
                    }
                }
            })
        }.launchIn(lifecycleScope)
    }
}
