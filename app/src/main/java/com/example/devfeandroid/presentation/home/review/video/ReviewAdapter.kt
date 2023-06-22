package com.example.devfeandroid.presentation.home.review.video

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.devfeandroid.R
import com.example.devfeandroid.databinding.BaseLoadMoreItemBinding
import com.example.devfeandroid.databinding.CommentReviewItemBinding
import com.example.devfeandroid.databinding.SeeMoreReviewItemBinding
import com.example.devfeandroid.extensions.INT_DEFAULT
import com.example.devfeandroid.extensions.getAppString
import com.example.devfeandroid.extensions.gone
import com.example.devfeandroid.extensions.loadImageUrl
import com.example.devfeandroid.extensions.setOnSafeClick
import com.example.devfeandroid.extensions.show
import com.example.devfeandroid.presentation.home.review.generic.CommentReviewDisplay
import com.example.devfeandroid.presentation.home.review.generic.IReviewAdapterListener
import com.example.devfeandroid.presentation.home.review.generic.ITEM_REVIEW_TYPE

class ReviewAdapter : ListAdapter<Any, RecyclerView.ViewHolder>(ReviewDiffCallBack()) {

    companion object {
        private const val PARENT_REVIEW = 0
        private const val CHILD_REVIEW = 1
        private const val SEE_MORE_TYPE = 2
        private const val LOAD_MORE_TYPE = 3
        private const val NOT_TYPE = -1
    }

    var listener: IReviewAdapterListener? = null

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return if (item is CommentReviewDisplay) {
            if (item.getCommentProduct().parentId == null) {
                PARENT_REVIEW
            } else {
                CHILD_REVIEW
            }
        } else if (item == ITEM_REVIEW_TYPE.LOAD_MORE) {
            LOAD_MORE_TYPE
        } else if (item is Map<*, *>) {
            SEE_MORE_TYPE
        } else {
            NOT_TYPE
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
                holder.onBind(getItem(position) as CommentReviewDisplay)
            }

            is ChildReviewVH -> {
                holder.onBind(getItem(position) as CommentReviewDisplay)
            }

            is SeeMoreVH -> {
                holder.onBind(getItem(position) as Map<String, Int>)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
        } else {
            when (holder) {
                is ParentReviewVH -> {
                    holder.onBind(getItem(position) as CommentReviewDisplay, payloads)
                }

//                is ChildReviewVH -> {
//                    holder.onBind(getItem(position) as CommentReviewDisplay)
//                }
//
//                is SeeMoreVH -> {
//                    holder.onBind(getItem(position) as Map<String, Int>)
//                }
            }
        }
    }

    inner class ParentReviewVH(private val binding: CommentReviewItemBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnSafeClick {
                val item = getItem(absoluteAdapterPosition) as? CommentReviewDisplay
                if (item != null) {
                    if (item.isSeeReplyComment == true) {
                        listener?.onSeeReplyComment(item.getCommentProduct().getIdComment())
                    } else {
                        listener?.onRemoveReplyComment(item.getCommentProduct().getIdComment())
                    }
                }
            }

            binding.llCommentReviewLike.setOnSafeClick {
                val item = getItem(absoluteAdapterPosition) as? CommentReviewDisplay
                if (item != null) {
                    listener?.onLikeHeart(item.getCommentProduct().getIdComment())
                }
            }

            binding.llCommentReviewReply.setOnSafeClick {
                val item = getItem(absoluteAdapterPosition) as? CommentReviewDisplay
                if (item != null) {
                    listener?.onReplyComment(item.getCommentProduct())
                }
            }
        }

        fun onBind(data: CommentReviewDisplay) {
            bindData(data)
        }

        fun onBind(data: CommentReviewDisplay, payloads: MutableList<Any>) {
            (payloads.firstOrNull() as? List<*>)?.forEach {
                when (it) {
                    UPDATE_STATE_LIKE_REVIEW_COMMENT_PAYLOAD -> {
                        checkMyLike(data.getCommentProduct().checkMyLike())
                    }

                    UPDATE_STATE_SEE_COMMENT_PAYLOAD -> {
                        //bindData(data)
                    }
                }
            }
        }

        private fun bindData(data: CommentReviewDisplay) {
            binding.apply {
                ivCommentReviewAvatarUser.loadImageUrl(data.getCommentProduct().getInfoUser().getUserImage())
                tvCommentReviewNameUser.text = data.getCommentProduct().getInfoUser().nameUser
                tvCommentReviewContent.text = data.getCommentProduct().getContentComment()
                tvCommentReviewTimePost.text = data.getCommentProduct().getTimePostComment()

                checkMyLike(data.getCommentProduct().checkMyLike())

                checkHasLike(data)
            }
        }

        private fun checkMyLike(data: Boolean) {
            if (data) {
                binding.ivCommentReviewLike.setImageResource(R.drawable.ic_heart_select)
            } else {
                binding.ivCommentReviewLike.setImageResource(R.drawable.ic_heart_un_select)
            }
        }

        private fun checkHasLike(data: CommentReviewDisplay) {
            if (data.getCommentProduct().getCountLike() != INT_DEFAULT) {
                binding.tvCommentReviewCountLike.show()
                binding.tvCommentReviewCountLike.text = data.getCommentProduct().getCountLike().toString()
            } else {
                binding.tvCommentReviewCountLike.gone()
            }
        }
    }

    inner class ChildReviewVH(private val binding: CommentReviewItemBinding) : RecyclerView.ViewHolder(binding.root) {

        init {

        }

        fun onBind(data: CommentReviewDisplay) {
            binding.apply {

                setUpCoordinates()

                ivCommentReviewAvatarUser.loadImageUrl(data.getCommentProduct().getInfoUser().getUserImage())
                tvCommentReviewNameUser.text = data.getCommentProduct().getInfoUser().nameUser
                tvCommentReviewContent.text = data.getCommentProduct().getContentComment()
                tvCommentReviewTimePost.text = data.getCommentProduct().getTimePostComment()

                if (data.getCommentProduct().getCountLike() != INT_DEFAULT) {
                    tvCommentReviewCountLike.show()
                } else {
                    tvCommentReviewCountLike.gone()
                }
            }
        }

        private fun setUpCoordinates() {
            val left = binding.tvCommentReviewNameUser.layoutParams as ViewGroup.MarginLayoutParams

            val offset = binding.ivCommentReviewAvatarUser.layoutParams as ViewGroup.MarginLayoutParams

            val params = binding.clCommentReviewRoot.layoutParams as ViewGroup.MarginLayoutParams

            params.leftMargin = left.leftMargin - offset.leftMargin
            binding.clCommentReviewRoot.layoutParams = params
        }
    }

    inner class SeeMoreVH(private val binding: SeeMoreReviewItemBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.llSeeMoreReview.setOnSafeClick {
                val item = getItem(absoluteAdapterPosition) as? Map<String, Int>
                if (item != null) {
                    var key: String? = null

                    item.forEach { (k, _) ->
                        key = k
                    }
                    key?.let { commentID ->
                        listener?.onSeeMoreReplyComment(commentID, absoluteAdapterPosition)
                    }
                }
            }
        }

        fun onBind(data: Map<String, Int>) {
            var key: String? = null

            data.forEach { (k, _) ->
                key = k
            }

            binding.tvSeeMoreReviewCountRemaining.text = getAppString(R.string.see_more_review, data[key])
        }
    }

    inner class LoadMoreVH(private val binding: BaseLoadMoreItemBinding) : RecyclerView.ViewHolder(binding.root)
}
