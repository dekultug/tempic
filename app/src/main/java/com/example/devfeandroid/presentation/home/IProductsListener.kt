package com.example.devfeandroid.presentation.home

import com.example.devfeandroid.data.model.postreview.PostReview

interface IProductsListener {
    fun onReviewImage(postReview: PostReview)
    fun onReviewVideo(postReview: PostReview)
}
