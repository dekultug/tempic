package com.example.devfeandroid.presentation.home.review.video

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.devfeandroid.R
import com.example.devfeandroid.data.model.review.CommentProduct
import com.example.devfeandroid.databinding.ReviewVideoFragmentBinding
import com.example.devfeandroid.extensions.STRING_DEFAULT
import com.example.devfeandroid.extensions.disable
import com.example.devfeandroid.extensions.enable
import com.example.devfeandroid.extensions.getAppColor
import com.example.devfeandroid.extensions.getAppString
import com.example.devfeandroid.extensions.gone
import com.example.devfeandroid.extensions.hide
import com.example.devfeandroid.extensions.setOnSafeClick
import com.example.devfeandroid.extensions.show
import com.example.devfeandroid.presentation.BaseFragment
import com.example.devfeandroid.presentation.MainActivity
import com.example.devfeandroid.presentation.home.review.generic.COMMENT_REVIEW_TYPE
import com.example.devfeandroid.presentation.home.review.generic.CommentReviewDisplay
import com.example.devfeandroid.presentation.home.review.generic.IReviewAdapterListener
import com.example.devfeandroid.presentation.widget.recyclerview.scroll.BaseLoadMoreRecyclerView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ReviewVideoFragment : BaseFragment() {

    companion object {
        private const val TIME_DELAY = 150L
        const val REVIEW_PRODUCT_KEY = "REVIEW_PRODUCT_KEY"
    }

    private val viewModel by lazy {
        ViewModelProvider(this)[ReviewVideoViewModel::class.java]
    }

    private var binding: ReviewVideoFragmentBinding? = null

    private val adapter by lazy {
        ReviewAdapter()
    }

    private var baseLoadMoreRecyclerView: BaseLoadMoreRecyclerView? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = ReviewVideoFragmentBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        removeListener()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity!!.showBottomNav(false)
        setUpAdapter()
        setEventView()
        obersever()
    }

    private fun setEventView() {
        binding!!.ivReviewVideoClose.setOnSafeClick {
            onBackPressFragment()
        }

        binding!!.flReviewRoot.setOnSafeClick {
            onBackPressFragment()
        }

        binding!!.edtReviewVideo.addTextChangedListener { content ->
            if (content.toString().trim().isEmpty()) {
                binding!!.ivReviewVideoSend.setColorFilter(getAppColor(R.color.gray))
                binding!!.ivReviewVideoSend.disable()
            } else {
                binding!!.ivReviewVideoSend.setColorFilter(getAppColor(R.color.main_color))
                binding!!.ivReviewVideoSend.enable()
            }
        }

        binding!!.ivReviewVideoSend.setOnSafeClick {
            if (viewModel.actionReviewType != COMMENT_REVIEW_TYPE.REPLY_COMMENT) {
                viewModel.commentProduct = CommentProduct(
                    commentId = (1000..10000000).random().toString(),
                    userInfo = viewModel.myUserInfo,
                    parentId = null
                )
            }
            viewModel.commentProduct?.let {
                viewModel.createComment(binding!!.edtReviewVideo.text.toString())
            }
            hideKeyBoardReview()
        }
    }

    private fun setUpAdapter() {

        binding!!.rvReviewVideo.adapter = this@ReviewVideoFragment.adapter
        binding!!.rvReviewVideo.layoutManager = LinearLayoutManager(context)

        baseLoadMoreRecyclerView = object : BaseLoadMoreRecyclerView(binding!!.rvReviewVideo.layoutManager!!) {
            override fun onLoadMore() {
                viewModel.getListReview(true)
                baseLoadMoreRecyclerView!!.isLoading = true
            }
        }

        baseLoadMoreRecyclerView?.let {
            binding!!.rvReviewVideo.addOnScrollListener(it)
        }

        addListener()
    }

    private fun addListener() {
        adapter.listener = object : IReviewAdapterListener {
            override fun onSeeReplyComment(commentID: String) {
                viewModel.getListChildReview(commentID)
            }

            override fun onSeeMoreReplyComment(commentID: String, position: Int) {
                viewModel.getListChildReview(commentID, true)
            }

            override fun onLikeHeart(commentID: String) {
                viewModel.interactReview(commentID)
            }

            override fun onRemoveReplyComment(commentID: String) {
                viewModel.removeListChildReview(commentID)
            }

            override fun onReplyComment(commentProduct: CommentProduct) {

                showKeyBoardReview()

                binding!!.llReviewVideoReplyUser.show()
                binding!!.tvReviewVideoReplyUserName.text = commentProduct.getInfoUser().getUserName()
                viewModel.commentProduct = CommentProduct(
                    commentId = (1000..1000000).random().toString(),
                    parentId = commentProduct.commentId,
                    userInfo = viewModel.myUserInfo
                )
                viewModel.actionReviewType = COMMENT_REVIEW_TYPE.REPLY_COMMENT

                binding!!.tvReviewVideoCancelReply.setOnSafeClick {
                    binding!!.llReviewVideoReplyUser.hide()
                }
            }
        }
    }

    private fun obersever() {

        lifecycleScope.launchWhenCreated {
            viewModel.product.collect { data ->
                binding!!.apply {
                    tvReviewVideoTitle.text = data.getTitleProductsDisplay()
                    tvReviewVideoDescription.text = data.getDescriptionProduct()
                    tvReviewVideoTimePost.text = data.getTimePostProduct()
                }
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.commentReviewState.collect {
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
                            binding!!.tvReviewVideoCountComment.text = getAppString(R.string.count_review, viewModel.totalReview.toString())
                            binding!!.llReviewVideoReplyUser.gone()

                            adapter.submitList(list)
                            baseLoadMoreRecyclerView!!.isLoading = false
                            baseLoadMoreRecyclerView!!.lastPage = viewModel.page > 7

                            viewModel.resetData()

                            if (viewModel.indexScroll != -1) {
                                scrollToIndex()
                            }
                        }
                    }
                })
            }
        }
    }

    private fun removeListener() {
        adapter.listener = null
    }

    private fun hideKeyBoardReview() {
        hideKeyBoard()

        binding!!.edtReviewVideo.setText(STRING_DEFAULT)
        binding!!.edtReviewVideo.clearFocus()
    }

    private fun showKeyBoardReview() {
        showKeyBoard()

        binding!!.edtReviewVideo.requestFocus()
    }

    private fun scrollToIndex() {
        lifecycleScope.launch {
            delay(TIME_DELAY)
            binding!!.rvReviewVideo.scrollToPosition(viewModel.indexScroll)
            viewModel.indexScroll = -1
        }
    }
}
