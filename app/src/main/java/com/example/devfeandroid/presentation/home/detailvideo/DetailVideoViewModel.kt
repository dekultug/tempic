package com.example.devfeandroid.presentation.home.detailvideo

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.devfeandroid.data.model.postreview.PostReview
import com.example.devfeandroid.data.model.userinfo.UserInfo
import kotlinx.coroutines.flow.MutableStateFlow

class DetailVideoViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {

    private val _product = savedStateHandle.get<PostReview>(DetailVideoFragment.DETAIL_PRODUCT_ITEM)
    val product: MutableStateFlow<PostReview> = MutableStateFlow(PostReview())

    var userInfo: UserInfo? = null

    init {
        if (_product != null) {
            product.value = _product
        }
    }
}
