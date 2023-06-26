package com.example.devfeandroid.presentation.home.review.image

import com.example.devfeandroid.data.model.producthome.review.CommentProduct

interface IReviewImageListener {

    fun onSeeReplyComment(commentID: String)

    fun onRemoveReplyComment(commentID: String)

    fun onSeeMoreReplyComment(commentID: String, position: Int)

    fun onLikeHeart(commentID: String)

    fun onReplyComment(commentProduct: CommentProduct)

}
