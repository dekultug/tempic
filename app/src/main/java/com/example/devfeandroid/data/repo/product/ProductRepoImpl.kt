package com.example.devfeandroid.data.repo.product

import com.example.devfeandroid.data.mockProduct
import com.example.devfeandroid.data.model.product.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ProductRepoImpl: IProductRepo {
    override fun getListProduct(): Flow<List<Product>> {
        return flow {
            val list:MutableList<Product> = arrayListOf()
            for (i in 0 until 100){
                list.add(mockProduct())
            }
        }
    }
}