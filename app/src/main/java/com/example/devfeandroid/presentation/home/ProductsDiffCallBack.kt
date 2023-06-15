package com.example.devfeandroid.presentation.home

import androidx.recyclerview.widget.DiffUtil
import com.example.devfeandroid.data.model.producthome.Products

class ProductsDiffCallBack : DiffUtil.ItemCallback<Products>() {
    override fun areItemsTheSame(oldItem: Products, newItem: Products): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Products, newItem: Products): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }
}
