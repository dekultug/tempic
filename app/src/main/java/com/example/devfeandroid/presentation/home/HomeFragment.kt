package com.example.devfeandroid.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.devfeandroid.R
import com.example.devfeandroid.data.model.postreview.HOME_FILTER
import com.example.devfeandroid.data.model.postreview.PostReview
import com.example.devfeandroid.databinding.HomeFragmentBinding
import com.example.devfeandroid.presentation.BaseFragment
import com.example.devfeandroid.presentation.MainActivity
import com.example.devfeandroid.presentation.home.detailvideo.DetailVideoFragment
import com.example.devfeandroid.presentation.home.review.image.ReviewImageFragment
import com.example.devfeandroid.presentation.widget.filter.FilterView
import com.example.devfeandroid.presentation.widget.recyclerview.scroll.BaseLoadMoreRecyclerView

class HomeFragment : BaseFragment() {

    private val viewModel by lazy { ViewModelProvider(this)[HomeViewModel::class.java] }

    private var binding: HomeFragmentBinding? = null

    private val postReviewAdapter by lazy {
        PostReviewAdapter()
    }

    private var baseLoadMoreRecyclerView: BaseLoadMoreRecyclerView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = HomeFragmentBinding.inflate(inflater, container, false)
        mainActivity!!.showBottomNav(true)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setEventView()
        setAdapter()
        oberserverData()
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
        removeListener()
    }

    private fun setEventView() {
        binding!!.fvHomeTop.listener = object : FilterView.IFilterViewEvent {
            override fun onFilter(title: String, position: Int) {
                HOME_FILTER.valueNameOf(title)?.let {
                    binding!!.rvHome.scrollToPosition(0)
                    viewModel.currentFilter = it
                    viewModel.getListProduct(true)
                }
            }
        }

        binding!!.srlHomeRefresh.apply {
            setOnRefreshListener {
                viewModel.getListProduct(true)
            }
        }
    }

    private fun setAdapter() {
        binding!!.rvHome.adapter = postReviewAdapter
        val manager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding!!.rvHome.layoutManager = manager

        baseLoadMoreRecyclerView = object : BaseLoadMoreRecyclerView(binding!!.rvHome.layoutManager!!) {
            override fun onLoadMore() {
                viewModel.getListProduct()
                baseLoadMoreRecyclerView?.isLoading = true
            }
        }

        baseLoadMoreRecyclerView?.let {
            binding!!.rvHome.addOnScrollListener(it)
        }

        addListener()
    }

    private fun oberserverData() {
        lifecycleScope.launchWhenCreated {
            viewModel.productListState.collect {
                mainActivity!!.setState(it, object : MainActivity.IUIState<MutableList<PostReview>> {
                    override fun onSuccess(data: MutableList<PostReview>) {
                        postReviewAdapter.submitList(data)
                        baseLoadMoreRecyclerView?.isLoading = false
                        baseLoadMoreRecyclerView?.lastPage = viewModel.page > 7

                        binding!!.srlHomeRefresh.isRefreshing = false
                    }
                })
            }
        }
    }

    private fun addListener() {
        postReviewAdapter.listener = object : IProductsListener {
            override fun onReviewImage(postReview: PostReview) {
                mainActivity!!.replaceFragment(
                    containerID = R.id.flMainContainerFragment,
                    fragment = ReviewImageFragment(),
                    bundle = bundleOf(ReviewImageFragment.REVIEW_IMAGE_PRODUCT_KEY to postReview)
                )
            }

            override fun onReviewVideo(postReview: PostReview) {
                mainActivity!!.replaceFragment(
                    containerID = R.id.flMainContainerFragment,
                    fragment = DetailVideoFragment(),
                    bundle = bundleOf(DetailVideoFragment.DETAIL_PRODUCT_ITEM to postReview)
                )
            }
        }
    }

    private fun removeListener() {
        postReviewAdapter.listener = null
    }
}
