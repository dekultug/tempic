package com.example.devfeandroid.presentation.home.review.video

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.example.devfeandroid.presentation.home.review.generic.CommentReviewDisplay

const val UPDATE_STATE_LIKE_REVIEW_COMMENT_PAYLOAD = "UPDATE_STATE_LIKE_REVIEW_COMMENT_PAYLOAD"
const val UPDATE_STATE_SEE_COMMENT_PAYLOAD = "UPDATE_STATE_SEE_COMMENT_PAYLOAD"

class ReviewDiffCallBack : DiffUtil.ItemCallback<Any>() {
    override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
        return when {
            oldItem is CommentReviewDisplay && newItem is CommentReviewDisplay -> {
                oldItem.getCommentProduct().commentId == newItem.getCommentProduct().commentId
            }

            oldItem is String && newItem is String -> {
                oldItem == newItem
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
                oldItem.isSeeReplyComment == newItem.isSeeReplyComment
                        && oldItem.getCommentProduct().checkMyLike() == newItem.getCommentProduct().checkMyLike()
                        && oldItem.getCommentProduct().getListReplyComment().size == newItem.getCommentProduct().getListReplyComment().size

            }

            oldItem is String && newItem is String -> {
                oldItem == newItem
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
                    list.add(UPDATE_STATE_LIKE_REVIEW_COMMENT_PAYLOAD)
                }

                if (
                    oldItem.isSeeReplyComment != newItem.isSeeReplyComment ||
                    oldItem.getCommentProduct().getListReplyComment().size != newItem.getCommentProduct().getListReplyComment().size
                ) {
                    list.add(UPDATE_STATE_SEE_COMMENT_PAYLOAD)
                }
            }
        }

        return list.ifEmpty { null }
    }
}
