package com.example.devfeandroid.widget.filter.data

enum class FILTER_TYPE(val value: Int) {
    HOME(0);

    companion object {
        fun valueOfName(type: Int): FILTER_TYPE? {
            val item = values().find {
                it.value == type
            }
            return item
        }
    }
}
