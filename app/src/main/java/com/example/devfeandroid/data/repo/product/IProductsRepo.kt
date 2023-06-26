package com.example.devfeandroid.data.repo.product

import com.example.devfeandroid.data.model.postreview.HOME_FILTER
import com.example.devfeandroid.data.model.postreview.PostReview
import com.example.devfeandroid.data.model.postreview.review.CommentProduct
import com.example.devfeandroid.data.model.postreview.review.ReviewProduct
import kotlinx.coroutines.flow.Flow

interface IProductsRepo {
    fun getProductList(type: HOME_FILTER,page: Int = 0): List<PostReview>

    fun getListReviewProducts(parent: Boolean = true): Flow<ReviewProduct>

    fun getListChildReview(parentId: String): Flow<List<CommentProduct>>
}
