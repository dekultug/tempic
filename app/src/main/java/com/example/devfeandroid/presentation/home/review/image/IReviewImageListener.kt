package com.example.devfeandroid.presentation.home.review.image

import com.example.devfeandroid.data.model.postreview.review.CommentPost

interface IReviewImageListener {

    fun onSeeReplyComment(commentID: String)

    fun onRemoveReplyComment(commentID: String)

    fun onSeeMoreReplyComment(commentID: String, position: Int)

    fun onLikeHeart(commentID: String)

    fun onReplyComment(commentPost: CommentPost)

}
