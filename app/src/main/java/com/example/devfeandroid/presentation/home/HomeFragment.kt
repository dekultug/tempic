package com.example.devfeandroid.presentation.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.devfeandroid.AppConfig
import com.example.devfeandroid.data.model.producthome.HOME_FILTER
import com.example.devfeandroid.data.model.producthome.Products
import com.example.devfeandroid.databinding.HomeFragmentBinding
import com.example.devfeandroid.presentation.BaseFragment
import com.example.devfeandroid.presentation.MainActivity
import com.example.devfeandroid.widget.bottomnav.TAB_BOTTOM_NAV
import com.example.devfeandroid.widget.filter.FilterView
import com.example.devfeandroid.widget.recyclerview.scroll.BaseLoadMoreRecyclerView

class HomeFragment : BaseFragment() {

    private val viewModel by lazy { ViewModelProvider(this)[HomeViewModel::class.java] }

    private var binding: HomeFragmentBinding? = null

    private val productsAdapter by lazy {
        ProductsAdapter()
    }

    private var baseLoadMoreRecyclerView: BaseLoadMoreRecyclerView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = HomeFragmentBinding.inflate(inflater, container, false)
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
    }

    private fun setEventView() {

        if (mainActivity!!.saveFilterSelect.containsKey(TAB_BOTTOM_NAV.HOME)) {
            binding!!.fvHomeTop.selectItem(mainActivity!!.saveFilterSelect[TAB_BOTTOM_NAV.HOME]!!)
        }

        binding!!.fvHomeTop.listener = object : FilterView.IFilterViewEvent {
            override fun onFilter(title: String, position: Int) {
                mainActivity!!.saveFilterSelect[TAB_BOTTOM_NAV.HOME] = position
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
        binding!!.rvHome.adapter = productsAdapter
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
    }

    private fun oberserverData() {
        lifecycleScope.launchWhenCreated {
            viewModel.productListState.collect {
                mainActivity!!.setState(it, object : MainActivity.IUIState<MutableList<Products>> {
                    override fun onSuccess(data: MutableList<Products>) {
                        productsAdapter.submitList(data)
                        Log.d("tunglvv", "onSuccess: ${data.size}")
                        baseLoadMoreRecyclerView?.isLoading = false
                        if (viewModel.page == 0) {
                            baseLoadMoreRecyclerView?.lastPage = data.size < AppConfig.LIMIT_ITEM
                        } else {
                            baseLoadMoreRecyclerView?.lastPage = (data.size / viewModel.page) < AppConfig.LIMIT_ITEM
                        }
                        binding!!.srlHomeRefresh.isRefreshing = false
                    }
                })
            }
        }
    }
}
