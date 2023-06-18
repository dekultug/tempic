package com.example.devfeandroid.presentation.home.review

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.devfeandroid.data.model.producthome.Products
import com.example.devfeandroid.data.model.review.CommentProduct
import com.example.devfeandroid.databinding.BaseLoadMoreItemBinding
import com.example.devfeandroid.databinding.CommentReviewItemBinding
import com.example.devfeandroid.databinding.SeeMoreReviewItemBinding
import com.example.devfeandroid.databinding.TitleReviewItemBinding
import com.example.devfeandroid.extensions.INT_DEFAULT
import com.example.devfeandroid.extensions.gone
import com.example.devfeandroid.extensions.loadImageUrl
import com.example.devfeandroid.extensions.show

class ReviewAdapter : ListAdapter<Any, RecyclerView.ViewHolder>(ReviewDiffCallBack()) {

    companion object {
        private const val PARENT_REVIEW = 0
        private const val CHILD_REVIEW = 1
        private const val SEE_MORE_TYPE = 2
        private const val LOAD_MORE_TYPE = 3
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return if (item is CommentProduct) {
            if (item.parentId == null) {
                PARENT_REVIEW
            } else {
                CHILD_REVIEW
            }
        } else if (item == REVIEW_TYPE.LOAD_MORE) {
            LOAD_MORE_TYPE
        } else {
            SEE_MORE_TYPE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            PARENT_REVIEW -> ParentReviewVH(CommentReviewItemBinding.inflate(layoutInflater, parent, false))
            CHILD_REVIEW -> ChildReviewVH(CommentReviewItemBinding.inflate(layoutInflater, parent, false))
            SEE_MORE_TYPE -> SeeMoreVH(SeeMoreReviewItemBinding.inflate(layoutInflater, parent, false))
            LOAD_MORE_TYPE -> LoadMoreVH(BaseLoadMoreItemBinding.inflate(layoutInflater, parent, false))
            else -> ParentReviewVH(CommentReviewItemBinding.inflate(layoutInflater, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ParentReviewVH -> {
                holder.onBind(getItem(position) as CommentProduct)
            }

            is ChildReviewVH -> {
                holder.onBind(getItem(position) as CommentProduct)
            }

            is SeeMoreVH -> {

            }
        }
    }

    inner class ParentReviewVH(private val binding: CommentReviewItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: CommentProduct) {
            binding.apply {
                ivCommentReviewAvatarUser.loadImageUrl(data.getInfoUser().getUserImage())
                tvCommentReviewNameUser.text = data.getInfoUser().nameUser
                tvCommentReviewContent.text = data.getContentComment()
                tvCommentReviewTimePost.text = data.getTimePostComment()

                if (data.getCountLike() != INT_DEFAULT) {
                    tvCommentReviewCountLike.show()
                } else {
                    tvCommentReviewCountLike.gone()
                }
            }
        }
    }

    inner class ChildReviewVH(private val binding: CommentReviewItemBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            val left = binding.tvCommentReviewNameUser.layoutParams as ViewGroup.MarginLayoutParams
            val params = binding.clCommentReviewRoot.layoutParams as ViewGroup.MarginLayoutParams

            params.leftMargin = left.leftMargin
            binding.clCommentReviewRoot.layoutParams = params
        }

        fun onBind(data: CommentProduct) {
            binding.apply {
                ivCommentReviewAvatarUser.loadImageUrl(data.getInfoUser().getUserImage())
                tvCommentReviewNameUser.text = data.getInfoUser().nameUser
                tvCommentReviewContent.text = data.getContentComment()
                tvCommentReviewTimePost.text = data.getTimePostComment()

                if (data.getCountLike() != INT_DEFAULT) {
                    tvCommentReviewCountLike.show()
                } else {
                    tvCommentReviewCountLike.gone()
                }
            }
        }
    }

    inner class SeeMoreVH(private val binding: SeeMoreReviewItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    inner class LoadMoreVH(private val binding: BaseLoadMoreItemBinding) : RecyclerView.ViewHolder(binding.root)
}
