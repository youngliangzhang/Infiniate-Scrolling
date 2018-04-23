package com.dmsoft.infinite.utils

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView

abstract class PageRecyclerViewScrollListener : RecyclerView.OnScrollListener() {

    companion object {
        private const val visibleThreshold = 5
    }

    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        recyclerView?.let {
            val layoutManager = it.layoutManager as GridLayoutManager
            val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
            val totalCount = it.adapter.itemCount
            if (totalCount <= lastVisibleItem + visibleThreshold)
                loadMoreItems()
        }
    }

    abstract fun loadMoreItems()
}