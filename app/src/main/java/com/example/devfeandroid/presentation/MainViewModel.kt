package com.example.devfeandroid.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private var listFollow: MutableList<String> = arrayListOf()

    private var _followState = MutableStateFlow<Boolean>(false)
    val followState = _followState.asStateFlow()

    fun follow(id: String) {
        listFollow.add(id)
    }

    fun unFollow(id: String) {
        var index = listFollow.indexOfFirst {
            it == id
        }
        if (index in 0 until listFollow.size) {
            listFollow.removeAt(index)
        }
    }

    fun getStatusFollow(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val index = listFollow.indexOfFirst {
                it == id
            }
            _followState.value = index in 0 until listFollow.size
        }
    }
}
