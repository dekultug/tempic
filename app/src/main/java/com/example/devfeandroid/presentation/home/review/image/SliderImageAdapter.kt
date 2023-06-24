package com.example.devfeandroid.presentation.home.review.image

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.devfeandroid.databinding.SliderImageItemBinding
import com.example.devfeandroid.extensions.loadImageUrl

class SliderImageAdapter : ListAdapter<String, SliderImageAdapter.SliderImageVH>(SliderImageDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderImageVH {
        return SliderImageVH(
            SliderImageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: SliderImageVH, position: Int) {
        holder.onBind(getItem(position))
    }

    inner class SliderImageVH(private val binding: SliderImageItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: String) {
            binding.ivSliderImage.loadImageUrl(data)
        }
    }

    class SliderImageDiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
}
