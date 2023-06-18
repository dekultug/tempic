package com.example.devfeandroid.presentation.home.detailvideo

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.devfeandroid.data.model.producthome.Products
import com.example.devfeandroid.data.model.userinfo.UserInfo
import kotlinx.coroutines.flow.MutableStateFlow

class DetailVideoViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {

    private val _product = savedStateHandle.get<Products>(DetailVideoFragment.DETAIL_PRODUCT_ITEM)
    val product: MutableStateFlow<Products> = MutableStateFlow(Products())

    var userInfo: UserInfo? = null

    init {
        if (_product != null) {
            product.value = _product
        }
    }
}
