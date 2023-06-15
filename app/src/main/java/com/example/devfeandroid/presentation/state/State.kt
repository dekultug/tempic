package com.example.devfeandroid.presentation.state

import com.example.devfeandroid.extensions.STRING_DEFAULT

//class State<Data>(
//    var stateData: StateData<Data>,
//    var listener: IUIState<Data>
//) {
//
//    init {
//        when (stateData) {
//            is Loading<*> -> {
//                listener.onLoading()
//            }
//
//            is Error<*> -> {
//                listener.onError((stateData as Error<*>).message ?: STRING_DEFAULT)
//            }
//
//            is Success<Data> -> {
//                (stateData as Success<Data>).data?.let { listener.onSuccess(it) }
//            }
//        }
//    }
//
//    interface IUIState<Data> {
//        fun onLoading() {}
//        fun onError(s: String) {}
//        fun onSuccess(data: Data) {}
//    }
//}
