package com.example.devfeandroid.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.devfeandroid.data.model.producthome.Products
import com.example.devfeandroid.databinding.ProductsItemBinding
import com.example.devfeandroid.extensions.gone
import com.example.devfeandroid.extensions.loadImageUrl
import com.example.devfeandroid.extensions.setOnSafeClick
import com.example.devfeandroid.extensions.show

class ProductsAdapter : ListAdapter<Products, ProductsAdapter.ProductsVH>(ProductsDiffCallBack()) {

    companion object {
        private const val ITEM_TYPE = 0
    }

    private val mapHeight: MutableMap<Int, Int> = HashMap()

    var listener: IProductsListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsVH {
        return ProductsVH(ProductsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ProductsVH, position: Int) {
        holder.onBind(getItem(position))
    }

    override fun onBindViewHolder(holder: ProductsVH, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
        } else {
            holder.onBind(getItem(position), payloads)
        }
    }

    inner class ProductsVH(private val binding: ProductsItemBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnSafeClick {
                val item = getItem(absoluteAdapterPosition)
                if (item.isHasVideo()) {
                    listener?.onReviewVideo(item)
                } else {
                    listener?.onReviewImage(item)
                }
            }
        }

        fun onBind(data: Products) {
            binding.ivProducts.loadImageUrl(data.getImageProductsDisplay())
            if (binding.ivProducts.height != 0) {
                if (!mapHeight.containsKey(absoluteAdapterPosition)) {
                    mapHeight[absoluteAdapterPosition] = binding.ivProducts.height
                } else {
                    binding.ivProducts.layoutParams = ViewGroup.LayoutParams(binding.ivProducts.width, mapHeight[absoluteAdapterPosition]!!)
                }
            }
            binding.tvProductsContent.text = data.getTitleProductsDisplay()
            Glide.with(binding.root.context).load(data.getAvatarUserInfo()).into(binding.ivProductsImageUser)
            binding.tvProductsNameUser.text = data.getUserName()
            binding.tvProductsCountHeart.text = data.getCountNumberHeart().toString()
            if (data.isHasVideo()) {
                binding.ivProductsPlayVideo.show()
            } else {
                binding.ivProductsPlayVideo.gone()
            }
        }

        fun onBind(data: Products, payload: List<Any>) {

        }
    }

}
