package com.example.devfeandroid.data.model.producthome

import com.example.devfeandroid.R
import com.example.devfeandroid.extensions.getAppString

enum class HOME_FILTER(val value: String) {
    ALL(getAppString(R.string.all)),
    HOT(getAppString(R.string.hot)),
    NOW(getAppString(R.string.now)),
    FOLLOW(getAppString(R.string.follow));

    companion object {
        fun valueNameOf(value: String): HOME_FILTER? {
            val item = values().find {
                it.value == value
            }
            return item ?: throw Exception("Không tìm thấy HOME_FILTER với value ${value}")
        }
    }
}
