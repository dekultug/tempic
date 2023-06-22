package com.example.devfeandroid.presentation.home.review.generic

import com.example.devfeandroid.data.model.review.CommentProduct

interface IReviewAdapterListener {

    fun onSeeReplyComment(commentID: String)

    fun onRemoveReplyComment(commentID: String)

    fun onSeeMoreReplyComment(commentID: String,position: Int)

    fun onLikeHeart(commentID: String)

    fun onReplyComment(commentProduct: CommentProduct)

}
