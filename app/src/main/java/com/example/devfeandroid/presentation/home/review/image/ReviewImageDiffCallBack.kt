package com.example.devfeandroid.presentation.home.review.image

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.example.devfeandroid.presentation.home.review.generic.CommentReviewDisplay
import com.example.devfeandroid.presentation.home.review.video.UPDATE_STATE_LIKE_REVIEW_COMMENT_PAYLOAD

const val UPDATE_STATE_SEE_DETAIL_REVIEW = "UPDATE_STATE_SEE_DETAIL_REVIEW"
const val UPDATE_STATE_LIKE_REVIEW_PAYLOAD = "UPDATE_STATE_LIKE_REVIEW_PAYLOAD"

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
                        && oldItem.isSeeReplyComment == newItem.isSeeReplyComment
                        && oldItem.getCommentProduct().checkMyLike() == newItem.getCommentProduct().checkMyLike()
            }

            else -> {
                oldItem.hashCode() == newItem.hashCode()
            }
        }
    }

    override fun getChangePayload(oldItem: Any, newItem: Any): Any? {
        val list: MutableList<Any> = arrayListOf()
        when {
            oldItem is CommentReviewDisplay && newItem is CommentReviewDisplay -> {

                if (oldItem.getCommentProduct().checkMyLike() != newItem.getCommentProduct().checkMyLike()) {
                    list.add(UPDATE_STATE_LIKE_REVIEW_PAYLOAD)
                }

                if (oldItem.isSeeReplyComment != newItem.isSeeReplyComment) {
                    list.add(UPDATE_STATE_SEE_DETAIL_REVIEW)
                }
            }
        }
        return list.ifEmpty { null }
    }
}
