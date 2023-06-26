package com.example.devfeandroid.data.repo

import com.example.devfeandroid.data.repo.postreview.IPostReviewRepo
import com.example.devfeandroid.data.repo.postreview.PostReviewImpl

object Repository {

    private val productImpl by lazy { PostReviewImpl() }

    fun getProductRepo(): IPostReviewRepo{
        return productImpl
    }

}
