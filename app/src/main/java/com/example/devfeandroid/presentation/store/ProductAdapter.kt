package com.example.devfeandroid.presentation.store

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.devfeandroid.data.model.product.Product
import com.example.devfeandroid.databinding.InfoProductItemBinding
import com.example.devfeandroid.extensions.STRING_DEFAULT
import com.example.devfeandroid.extensions.loadImageUrl

class ProductAdapter : ListAdapter<Product,ProductAdapter.ProductVH>(ProductDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductVH {
        return ProductVH(InfoProductItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ProductVH, position: Int) {
        holder.onBind(getItem(position))
    }

    inner class ProductVH(private val binding: InfoProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: Product) {
            with(binding) {
                tvInfoProductName.text = data.nameProduct
                tvInfoProductDescription.text = data.descriptionProduct
                ivInfoProduct.loadImageUrl(data.imageProduct ?: STRING_DEFAULT)
            }
        }
    }

    class ProductDiffCallBack : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
}