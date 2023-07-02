package com.example.devfeandroid.data.repo.postreview

import com.example.devfeandroid.data.model.postreview.HOME_FILTER
import com.example.devfeandroid.data.model.postreview.PostReview
import com.example.devfeandroid.data.model.postreview.review.CommentPost
import com.example.devfeandroid.data.model.postreview.review.ReviewPost
import kotlinx.coroutines.flow.Flow

interface IPostReviewRepo {
    fun getProductList(type: HOME_FILTER,page: Int = 0): List<PostReview>

    fun getListReviewProducts(parent: Boolean = true): Flow<ReviewPost>

    fun getListChildReview(parentId: String): Flow<List<CommentPost>>
}
