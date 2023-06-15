package com.example.devfeandroid.data.repo.producthome

import com.example.devfeandroid.data.model.producthome.HOME_FILTER
import com.example.devfeandroid.data.model.producthome.Products

interface IProductsHome {
    fun getProductList(type: HOME_FILTER,page: Int = 0): List<Products>
}
