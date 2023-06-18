package com.example.devfeandroid.presentation.home.review

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.devfeandroid.data.model.producthome.Products
import com.example.devfeandroid.data.repo.Repository
import com.example.devfeandroid.extensions.INT_DEFAULT
import com.example.devfeandroid.presentation.state.StateData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class ReviewViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {

    private val _product = savedStateHandle.get<Products>(ReviewFragment.REVIEW_PRODUCT_KEY)
    val product = MutableStateFlow(Products())

    private var _commentReviewState = MutableStateFlow<StateData<List<Any>>>(StateData.Init())
    val commentReviewState = _commentReviewState.asStateFlow()

    var totalReview: Int = INT_DEFAULT

    val list: MutableList<Any> = arrayListOf()

    var page = 0

    init {
        if (_product != null) {
            product.value = _product
        }
        getListReview()
    }

    fun getListReview(isLoadMore: Boolean = false) {
        viewModelScope.launch {

            val repo = Repository.getProductRepo()

            repo.getListReviewProducts()
                .onStart {
                    if (isLoadMore) {
                        page++
                        list.add(REVIEW_TYPE.LOAD_MORE)
                        _commentReviewState.value = StateData.Success(data = list)
                        delay(350)
                    } else {
                        _commentReviewState.value = StateData.Loading()
                    }
                }
                .catch {
                    _commentReviewState.value = StateData.Error(throwable = it)
                }
                .collect {
                    if (isLoadMore) {
                        val index = list.indexOfFirst {
                            it == REVIEW_TYPE.LOAD_MORE
                        }
                        if (index >= 0) {
                            list.removeAt(index)
                        }
                    }
                    totalReview = it.getTotalReview()
                    if (it.listComment != null) {
                        list.addAll(it.listComment!!)
                    }
                    _commentReviewState.value = StateData.Success(data = list)
                }
        }
    }
}
