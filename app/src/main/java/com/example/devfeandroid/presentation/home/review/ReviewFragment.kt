package com.example.devfeandroid.presentation.home.review

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.devfeandroid.R
import com.example.devfeandroid.databinding.ReviewFragmentBinding
import com.example.devfeandroid.extensions.getAppString
import com.example.devfeandroid.extensions.setOnSafeClick
import com.example.devfeandroid.presentation.BaseFragment
import com.example.devfeandroid.presentation.MainActivity
import com.example.devfeandroid.widget.recyclerview.scroll.BaseLoadMoreRecyclerView
import kotlinx.coroutines.flow.collect

class ReviewFragment : BaseFragment() {

    /**
     * binding.edtReplyComment.addTextChangedListener { content ->
    if (content.toString().trim().isEmpty()) {
    binding.ivReplyCommentSendAnswer.setColorFilter(getAppColor(R.color.gray))
    binding.ivReplyCommentSendAnswer.disable()
    } else {
    binding.ivReplyCommentSendAnswer.setColorFilter(getAppColor(R.color.orange_chapter))
    binding.ivReplyCommentSendAnswer.enable()
    }
    }
     */

    companion object {
        const val REVIEW_PRODUCT_KEY = "REVIEW_PRODUCT_KEY"
    }

    private val viewModel by lazy {
        ViewModelProvider(this)[ReviewViewModel::class.java]
    }

    private var binding: ReviewFragmentBinding? = null

    private val adapter by lazy {
        ReviewAdapter()
    }

    private var baseLoadMoreRecyclerView: BaseLoadMoreRecyclerView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = ReviewFragmentBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity!!.showBottomNav(false)
        setUpAdapter()
        setEventView()
        obersever()
    }

    private fun obersever() {

        lifecycleScope.launchWhenCreated {
            viewModel.product.collect { data ->
                binding!!.apply {
                    tvReviewTitle.text = data.getTitleProductsDisplay()
                    tvReviewDescription.text = data.getDescriptionProduct()
                    tvReviewTimePost.text = data.getTimePostProduct()
                }
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.commentReviewState.collect {
                mainActivity!!.setState(it, object : MainActivity.IUIState<List<Any>> {
                    override fun onSuccess(data: List<Any>) {

                        binding!!.tvReviewCountComment.text = getAppString(R.string.count_review, viewModel.totalReview.toString())

                        adapter.submitList(data)

                        baseLoadMoreRecyclerView!!.isLoading = false
                        baseLoadMoreRecyclerView!!.lastPage = viewModel.page > 7
                    }
                })
            }
        }
    }

    private fun setUpAdapter() {
        binding!!.rvReview.adapter = this@ReviewFragment.adapter
        binding!!.rvReview.layoutManager = LinearLayoutManager(context)

        baseLoadMoreRecyclerView = object : BaseLoadMoreRecyclerView(binding!!.rvReview.layoutManager!!) {
            override fun onLoadMore() {
                viewModel.getListReview(true)
                baseLoadMoreRecyclerView!!.isLoading = true
            }
        }

        baseLoadMoreRecyclerView?.let {
            binding!!.rvReview.addOnScrollListener(it)
        }
    }

    private fun setEventView() {
        binding!!.ivReviewClose.setOnSafeClick {
            onBackPressFragment()
        }

        binding!!.flReviewRoot.setOnSafeClick {
            onBackPressFragment()
        }
    }
}
