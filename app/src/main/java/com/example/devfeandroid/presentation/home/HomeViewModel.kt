package com.example.devfeandroid.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.devfeandroid.data.model.producthome.HOME_FILTER
import com.example.devfeandroid.data.model.producthome.Products
import com.example.devfeandroid.data.repo.Repository
import com.example.devfeandroid.data.repo.product.IProductsRepo
import com.example.devfeandroid.data.repo.product.ProductsImpl
import com.example.devfeandroid.presentation.state.StateData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private var _productListState = MutableStateFlow<StateData<MutableList<Products>>>(StateData.Init())
    val productListState = _productListState

    var currentFilter: HOME_FILTER = HOME_FILTER.ALL

    var page = 0

    var list: MutableList<Products> = arrayListOf()

    var isLoaded = false

    init {
        getListProduct(true)
    }

    fun getListProduct(isReload: Boolean = false) {
        viewModelScope.launch {
            val repo = Repository.getProductRepo()
            if (isReload) {
                _productListState.value = StateData.Loading()
                page = 1
                list.clear()
            } else {
                page++
            }
            delay(150)
            list.addAll(repo.getProductList(currentFilter, page))
            isLoaded = true
            _productListState.value = StateData.Success(list.toMutableList())

        }
    }
}
