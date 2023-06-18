package com.example.devfeandroid.data.repo

import com.example.devfeandroid.data.repo.product.IProductsRepo
import com.example.devfeandroid.data.repo.product.ProductsImpl

object Repository {

    private val productImpl by lazy { ProductsImpl() }

    fun getProductRepo(): IProductsRepo{
        return productImpl
    }

}
