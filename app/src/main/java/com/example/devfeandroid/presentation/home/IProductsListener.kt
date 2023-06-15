package com.example.devfeandroid.presentation.home

import com.example.devfeandroid.data.model.producthome.Products

interface IProductsListener {
    fun onReviewImage(products: Products)
    fun onReviewVideo(products: Products)
}
