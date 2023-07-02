package com.example.devfeandroid.presentation.store

import androidx.recyclerview.widget.DiffUtil

class StoreDiffCallBack : DiffUtil.ItemCallback<Any>() {
    override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
        return true
    }

    override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
        return false
    }
}