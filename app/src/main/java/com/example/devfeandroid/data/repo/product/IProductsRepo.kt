package com.example.devfeandroid.data.repo.product

import com.example.devfeandroid.data.model.producthome.HOME_FILTER
import com.example.devfeandroid.data.model.producthome.Products
import com.example.devfeandroid.data.model.review.ReviewProduct
import kotlinx.coroutines.flow.Flow

interface IProductsRepo {
    fun getProductList(type: HOME_FILTER,page: Int = 0): Flow<List<Products>>

    fun getListReviewProducts(): Flow<ReviewProduct>
}
