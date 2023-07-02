package com.example.devfeandroid.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.devfeandroid.databinding.BaseEmptyDataItemBinding
import com.example.devfeandroid.databinding.BaseLoadMoreItemBinding
import com.example.devfeandroid.databinding.BaseLoadingItemBinding

abstract class BaseAdapter<DATA> : ListAdapter<DATA, RecyclerView.ViewHolder>(BaseDiffUtil<DATA>()) {

    companion object {
        const val LOAD_MORE_TYPE = 0
        const val LOADING_TYPE = 1
        const val NORMAL_TYPE = 2
        const val EMPTY_TYPE = 3
        const val INVALID_RESOURCE = -1
    }

    private lateinit var myInflater: LayoutInflater

    @LayoutRes
    abstract fun getLayoutResource(viewType: Int): Int

    abstract fun onCreateViewHolder(viewType: Int, binding: ViewDataBinding): BaseVH<DATA>?

    open fun getItemViewTypeCustom(position: Int) = NORMAL_TYPE

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is Loading -> LOADING_TYPE
            is LoadMore -> LOAD_MORE_TYPE
            is Empty -> EMPTY_TYPE
            else -> getItemViewTypeCustom(position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (!::myInflater.isInitialized) {
            myInflater = LayoutInflater.from(parent.context)
        }

        return when (viewType) {
            LOADING_TYPE -> {
                LoadingVH(BaseLoadingItemBinding.inflate(myInflater, parent, false))
            }

            LOAD_MORE_TYPE -> {
                LoadMoreVH(BaseLoadMoreItemBinding.inflate(myInflater, parent, false))
            }

            EMPTY_TYPE -> {
                EmptyVH(BaseEmptyDataItemBinding.inflate(myInflater, parent, false))
            }

            else -> {
                if (getLayoutResource(viewType) != INVALID_RESOURCE) {
                    val binding = DataBindingUtil.inflate<ViewDataBinding>(myInflater, getLayoutResource(viewType), parent, false)
                    onCreateViewHolder(viewType, binding) as BaseVH<DATA>
                } else {
                    throw IllegalArgumentException("Can not find layout for type: $viewType")
                }
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is BaseVH<*>) {
            holder.onBind(getItem(position) as? Nothing)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
        } else {
            if (holder is BaseVH<*>) {
                holder.onBind(getItem(position) as? Nothing, payloads)
            }
        }
    }

    class LoadingVH(private val binding: BaseLoadingItemBinding) : BaseVH<Loading>(binding.root)

    class LoadMoreVH(private val binding: BaseLoadMoreItemBinding) : BaseVH<LoadMore>(binding.root)

    class EmptyVH(private val binding: BaseEmptyDataItemBinding) : BaseVH<Empty>(binding.root)

    class Loading(
        val title: String? = null,
        val icon: String? = null
    )

    class LoadMore(
        val title: String,
        val icon: String
    )

    class Empty()
}
