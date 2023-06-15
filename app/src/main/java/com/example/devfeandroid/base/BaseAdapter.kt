//package com.example.devfeandroid.base
//
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.ListAdapter
//import com.example.devfeandroid.databinding.BaseLoadMoreItemBinding
//import com.example.devfeandroid.databinding.BaseLoadingItemBinding
//
//open class BaseAdapter<DATA> : ListAdapter<DATA, BaseVH<DATA>>(BaseDiffUtil<DATA>()) {
//
//    companion object {
//        const val LOAD_MORE_TYPE = 0
//        const val LOADING_TYPE = 1
//    }
//
//    override fun getItemViewType(position: Int): Int {
//        return super.getItemViewType(position)
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseVH<DATA> {
//        return when (viewType) {
//            //ProductsVH(ProductsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
//            LOADING_TYPE -> {
//                LoadingVH(BaseLoadingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
//            }
//        }
//    }
//
//    override fun onBindViewHolder(holder: BaseVH<DATA>, position: Int) {
//
//    }
//
//    class LoadingVH(private val binding: BaseLoadingItemBinding) : BaseVH<Loading>(binding.root)
//
//    class LoadMoreVH(private val binding: BaseLoadMoreItemBinding) : BaseVH<LoadMore>(binding.root)
//
//    class Loading(
//        val title: String? = null,
//        val icon: String? = null
//    )
//
//    class LoadMore(
//        val title: String,
//        val icon: String
//    )
//}
