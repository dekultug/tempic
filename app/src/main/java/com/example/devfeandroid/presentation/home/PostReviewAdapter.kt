package com.example.devfeandroid.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.devfeandroid.data.model.postreview.PostReview
import com.example.devfeandroid.databinding.PostReviewItemBinding
import com.example.devfeandroid.extensions.gone
import com.example.devfeandroid.extensions.loadImageUrl
import com.example.devfeandroid.extensions.setOnSafeClick
import com.example.devfeandroid.extensions.show

class PostReviewAdapter : ListAdapter<PostReview, PostReviewAdapter.ProductsVH>(ProductsDiffCallBack()) {

    companion object {
        private const val ITEM_TYPE = 0
    }

    private val mapHeight: MutableMap<Int, Int> = HashMap()

    var listener: IProductsListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsVH {
        return ProductsVH(PostReviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
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

    inner class ProductsVH(private val binding: PostReviewItemBinding) : RecyclerView.ViewHolder(binding.root) {

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

        fun onBind(data: PostReview) {
            binding.ivPostReviewProduct.loadImageUrl(data.getImageProductsDisplay())
            if (binding.ivPostReviewProduct.height != 0) {
                if (!mapHeight.containsKey(absoluteAdapterPosition)) {
                    mapHeight[absoluteAdapterPosition] = binding.ivPostReviewProduct.height
                } else {
                    binding.ivPostReviewProduct.layoutParams = ViewGroup.LayoutParams(binding.ivPostReviewProduct.width, mapHeight[absoluteAdapterPosition]!!)
                }
            }
            binding.tvPostReviewDescription.text = data.getTitleProductsDisplay()
            Glide.with(binding.root.context).load(data.getAvatarUserInfo()).into(binding.ivPostReviewImageUser)
            binding.tvProductsNameUser.text = data.getUserName()
            binding.tvPostReviewCountHeart.text = data.getCountNumberHeart().toString()
            if (data.isHasVideo()) {
                binding.ivPostReviewPlayView.show()
            } else {
                binding.ivPostReviewPlayView.gone()
            }
        }

        fun onBind(data: PostReview, payload: List<Any>) {

        }
    }

}
