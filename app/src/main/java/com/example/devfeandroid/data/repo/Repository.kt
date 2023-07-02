package com.example.devfeandroid.data.repo

import com.example.devfeandroid.data.repo.postreview.IPostReviewRepo
import com.example.devfeandroid.data.repo.postreview.PostReviewImpl
import com.example.devfeandroid.data.repo.product.IProductRepo
import com.example.devfeandroid.data.repo.product.ProductRepoImpl

object Repository {

    private val postReviewImpl by lazy { PostReviewImpl() }

    private val productImpl by lazy { ProductRepoImpl() }
    fun getPostRepo(): IPostReviewRepo{
        return postReviewImpl
    }

    fun getProductRepo(): IProductRepo{
        return productImpl
    }
}
