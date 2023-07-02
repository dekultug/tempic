package com.example.devfeandroid.presentation.home

import androidx.recyclerview.widget.DiffUtil
import com.example.devfeandroid.data.model.postreview.PostReview

class ProductsDiffCallBack : DiffUtil.ItemCallback<PostReview>() {
    override fun areItemsTheSame(oldItem: PostReview, newItem: PostReview): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PostReview, newItem: PostReview): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }
}
