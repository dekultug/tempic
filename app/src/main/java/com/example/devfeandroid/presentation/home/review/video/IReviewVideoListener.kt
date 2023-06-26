package com.example.devfeandroid.presentation.home.review.video

import com.example.devfeandroid.data.model.postreview.review.CommentProduct

interface IReviewVideoListener {

    fun onSeeReplyComment(commentID: String)

    fun onRemoveReplyComment(commentID: String)

    fun onSeeMoreReplyComment(commentID: String,position: Int)

    fun onLikeHeart(commentID: String)

    fun onReplyComment(commentProduct: CommentProduct)

}
