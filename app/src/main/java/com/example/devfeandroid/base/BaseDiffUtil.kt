package com.example.devfeandroid.base

import androidx.recyclerview.widget.DiffUtil

open class BaseDiffUtil<DATA> : DiffUtil.ItemCallback<DATA>() {
    override fun areItemsTheSame(oldItem: DATA & Any, newItem: DATA & Any): Boolean {
        return true
    }

    override fun areContentsTheSame(oldItem: DATA & Any, newItem: DATA & Any): Boolean {
        return false
    }
}
