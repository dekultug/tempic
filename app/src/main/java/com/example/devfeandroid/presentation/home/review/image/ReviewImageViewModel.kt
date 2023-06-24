package com.example.devfeandroid.presentation.home.review.image

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.devfeandroid.data.model.producthome.Products
import com.example.devfeandroid.data.repo.Repository
import com.example.devfeandroid.extensions.INT_DEFAULT
import com.example.devfeandroid.presentation.home.review.generic.ITEM_REVIEW_TYPE
import com.example.devfeandroid.presentation.state.StateData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class ReviewImageViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {

    val product = savedStateHandle.get<Products>(ReviewImageFragment.REVIEW_IMAGE_PRODUCT_KEY)

    private var _reviewState = MutableStateFlow<StateData<List<Any>>>(StateData.Init())
    val reviewState = _reviewState.asStateFlow()

    val list: MutableList<Any> = arrayListOf()

    var page = 0

    init {
        getInfoReviewProduct(true)
    }

    fun getInfoReviewProduct(isReLoad: Boolean = false) {
        if (isReLoad) {
            list.clear()
            page = 0
        }

        product?.let {
            list.add(it.getAllImage())
            list.add(it)
        }
        list.add(REVIEW_IMAGE_TYPE.RELATIVE_PRODUCT)
        getListReview()
    }

    fun getListReview(isLoadMore: Boolean = false) {
        viewModelScope.launch {
            val repo = Repository.getProductRepo()
            repo.getListReviewProducts()
                .onStart {
                    if (isLoadMore) {
                        page++
                        list.add(REVIEW_IMAGE_TYPE.LOAD_MORE)
                        _reviewState.value = StateData.Success(data = list)
                        delay(1000)
                    } else {
                        _reviewState.value = StateData.Loading()
                    }
                }
                .catch {
                    _reviewState.value = StateData.Error(throwable = it)
                }
                .collect {

                    if (isLoadMore) {
                        val index = list.indexOfFirst {
                            it == REVIEW_IMAGE_TYPE.LOAD_MORE
                        }
                        if (index >= 0) {
                            list.removeAt(index)
                        }
                    } else {
                        list.add(it.totalComment ?: INT_DEFAULT)
                    }

                    list.addAll(it.listComment ?: arrayListOf())
                    _reviewState.value = StateData.Success(list)
                }
        }
    }
}
