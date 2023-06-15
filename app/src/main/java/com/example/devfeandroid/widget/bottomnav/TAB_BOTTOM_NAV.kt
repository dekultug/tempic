package com.example.devfeandroid.widget.bottomnav

enum class TAB_BOTTOM_NAV(val values: Int) {
    HOME(0), STORE(1), POST(2), GIFT(3), PERSONAL(4);

    companion object {
        fun valuesOfType(values: Int): TAB_BOTTOM_NAV {
            val item = values().find {
                it.values == values
            }
            return item?: throw Exception("ko tìm thấy tab với value = ${values}")
        }
    }
}
