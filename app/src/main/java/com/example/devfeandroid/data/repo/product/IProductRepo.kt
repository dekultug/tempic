package com.example.devfeandroid.data.repo.product

import com.example.devfeandroid.data.model.product.Product
import kotlinx.coroutines.flow.Flow

interface IProductRepo {

    fun getListProduct(): Flow<List<Product>>

}