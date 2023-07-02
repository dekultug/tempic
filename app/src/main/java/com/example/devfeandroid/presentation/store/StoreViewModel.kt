package com.example.devfeandroid.presentation.store

import androidx.lifecycle.ViewModel
import com.example.devfeandroid.presentation.state.StateData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class StoreViewModel : ViewModel() {

    private var _storeMainState = MutableStateFlow<StateData<List<Any>>>(StateData.Init())
    val storeMainState = _storeMainState.asStateFlow()

    init {

    }

    fun getStore() {

    }
}