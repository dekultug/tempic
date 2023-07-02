package com.example.devfeandroid.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseVH<DATA>(private val view: View) : RecyclerView.ViewHolder(view) {

    open fun onBind(data: DATA?) {

    }

    open fun onBind(data: DATA?, payloads: List<Any>) {

    }
}
