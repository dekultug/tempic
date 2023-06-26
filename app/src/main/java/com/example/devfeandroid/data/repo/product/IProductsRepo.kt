package com.example.devfeandroid.data.repo.product

import com.example.devfeandroid.data.model.producthome.HOME_FILTER
import com.example.devfeandroid.data.model.producthome.Products
import com.example.devfeandroid.data.model.producthome.review.CommentProduct
import com.example.devfeandroid.data.model.producthome.review.ReviewProduct
import kotlinx.coroutines.flow.Flow

interface IProductsRepo {
    fun getProductList(type: HOME_FILTER,page: Int = 0): List<Products>

    fun getListReviewProducts(parent: Boolean = true): Flow<ReviewProduct>

    fun getListChildReview(parentId: String): Flow<List<CommentProduct>>
}
