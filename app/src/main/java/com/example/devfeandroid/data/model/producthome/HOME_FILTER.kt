package com.example.devfeandroid.data.model.producthome

enum class HOME_FILTER(val value: String) {
    ALL("ALL"), HOT("Hot"), NOW("Now"), FOLLOW("Follow");

    companion object {
        fun valueNameOf(value: String): HOME_FILTER? {
            val item = values().find {
                it.value == value
            }
            return item?:throw Exception("Không tìm thấy HOME_FILTER với value ${value}")
        }
    }
}
