package com.example.devfeandroid.presentation.home.review.image

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.example.devfeandroid.presentation.home.review.generic.CommentReviewDisplay

class ReviewImageDiffCallBack : DiffUtil.ItemCallback<Any>() {
    override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
        return when {
            oldItem is CommentReviewDisplay && newItem is CommentReviewDisplay -> {
                oldItem.getCommentProduct().commentId == newItem.getCommentProduct().commentId
            }

            else -> {
                oldItem.hashCode() == newItem.hashCode()
            }
        }
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
        return when {
            oldItem is CommentReviewDisplay && newItem is CommentReviewDisplay -> {
                oldItem.getCommentProduct().commentId == newItem.getCommentProduct().commentId
            }

            else -> {
                oldItem.hashCode() == newItem.hashCode()
            }
        }
    }
}
