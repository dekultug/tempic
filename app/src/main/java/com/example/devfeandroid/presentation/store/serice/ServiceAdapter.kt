package com.example.devfeandroid.presentation.store.serice

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.devfeandroid.databinding.ServiceItemBinding
import com.example.devfeandroid.extensions.loadImageUrl

class ServiceAdapter: ListAdapter<ServiceDisplay,ServiceAdapter.ServiceVH>(ServiceDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceVH {
        return ServiceVH(
            ServiceItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: ServiceVH, position: Int) {
        holder.onBind(getItem(position))
    }

    inner class ServiceVH(private val binding: ServiceItemBinding): RecyclerView.ViewHolder(binding.root){

        fun onBind(data: ServiceDisplay){
            binding.ivService.setImageDrawable(data.getIcon())
            binding.tvService.text = data.getTitle()
        }
    }

    class ServiceDiffCallBack: DiffUtil.ItemCallback<ServiceDisplay>(){
        override fun areItemsTheSame(oldItem: ServiceDisplay, newItem: ServiceDisplay): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: ServiceDisplay, newItem: ServiceDisplay): Boolean {
            return oldItem == newItem
        }
    }
}