package com.example.devfeandroid.presentation.home.review

import androidx.recyclerview.widget.DiffUtil
import com.example.devfeandroid.data.model.review.CommentProduct

class ReviewDiffCallBack : DiffUtil.ItemCallback<Any>() {
    override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
        return if (oldItem is CommentProduct && newItem is CommentProduct) {
            oldItem.commentId == newItem.commentId
        } else {
            true
        }
    }

    override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }
}
