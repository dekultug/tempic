package com.example.devfeandroid.presentation.widget.recyclerview.scroll

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

abstract class BaseLoadMoreRecyclerView(
    private var layoutManager: RecyclerView.LayoutManager
) : RecyclerView.OnScrollListener() {

    abstract fun onLoadMore()

    var isLoading: Boolean = false

    var lastPage: Boolean = false

    private var isScrollDown = false

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        isScrollDown = dy > 0
        if (isScrollDown) {
            val totalItemCount = layoutManager.itemCount
            var pastVisibleItems = 0
            when (layoutManager) {
                is StaggeredGridLayoutManager -> {
                    val lastVisibleItemPositions = (layoutManager as StaggeredGridLayoutManager).findLastVisibleItemPositions(null)
                    pastVisibleItems = getLastVisibleItem(lastVisibleItemPositions)
                }
                is GridLayoutManager -> {
                    pastVisibleItems = (layoutManager as GridLayoutManager).findLastVisibleItemPosition()
                }
                is LinearLayoutManager -> {
                    pastVisibleItems = (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                }
            }

            if (isLoading || lastPage) return

            if (1 + pastVisibleItems >= totalItemCount) {
                onLoadMore()
            }
        }
    }

    private fun getLastVisibleItem(lastVisibleItemPositions: IntArray): Int {
        var maxSize = 0

        for (i in lastVisibleItemPositions.indices) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i]
            } else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i]
            }
        }

        return maxSize
    }
}
