package com.example.devfeandroid.presentation.home.review.image

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.devfeandroid.R
import com.example.devfeandroid.data.model.postreview.PostReview
import com.example.devfeandroid.databinding.BaseLoadMoreItemBinding
import com.example.devfeandroid.databinding.CommentReviewItemBinding
import com.example.devfeandroid.databinding.CountReviewItemBinding
import com.example.devfeandroid.databinding.InfoReviewItemBinding
import com.example.devfeandroid.databinding.RelativeProductItemBinding
import com.example.devfeandroid.databinding.SeeMoreReviewItemBinding
import com.example.devfeandroid.databinding.SliderReviewImageItemBinding
import com.example.devfeandroid.extensions.INT_DEFAULT
import com.example.devfeandroid.extensions.getAppString
import com.example.devfeandroid.extensions.gone
import com.example.devfeandroid.extensions.loadImageUrl
import com.example.devfeandroid.extensions.setOnSafeClick
import com.example.devfeandroid.extensions.show
import com.example.devfeandroid.presentation.home.review.generic.CommentReviewDisplay
import com.example.devfeandroid.presentation.home.review.video.UPDATE_STATE_SEE_COMMENT_PAYLOAD


class ReviewImageAdapter : ListAdapter<Any, RecyclerView.ViewHolder>(ReviewImageDiffCallBack()) {

    companion object {

        private const val SLIDER_TYPE = 0

        private const val INFO_TYPE = 1

        private const val RELATIVE_TYPE = 2

        private const val TOTAL_REVIEW_TYPE = 3

        private const val PARENT_REVIEW_TYPE = 4

        private const val CHILD_REVIEW_TYPE = 5

        private const val SEE_MORE_REVIEW = 6

        private const val LOAD_MORE_TYPE = 7
    }

    var listener: IReviewImageListener? = null

    var isDisableScroll = true

    override fun getItemViewType(position: Int): Int {

        return when (getItem(position)) {

            is List<*> -> SLIDER_TYPE

            is PostReview -> INFO_TYPE

            REVIEW_IMAGE_TYPE.RELATIVE_PRODUCT -> RELATIVE_TYPE

            is Int -> TOTAL_REVIEW_TYPE

            is CommentReviewDisplay -> {
                val item = getItem(position) as CommentReviewDisplay
                if (item.getCommentProduct().parentId == null) {
                    PARENT_REVIEW_TYPE
                } else {
                    CHILD_REVIEW_TYPE
                }
            }

            is Map<*, *> -> SEE_MORE_REVIEW

            REVIEW_IMAGE_TYPE.LOAD_MORE -> LOAD_MORE_TYPE

            else -> -1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            SLIDER_TYPE -> SliderProductVH(SliderReviewImageItemBinding.inflate(layoutInflater, parent, false))

            INFO_TYPE -> InfoProductVH(InfoReviewItemBinding.inflate(layoutInflater, parent, false))

            RELATIVE_TYPE -> RelativeProductVH(RelativeProductItemBinding.inflate(layoutInflater, parent, false))

            TOTAL_REVIEW_TYPE -> CountReviewVH(CountReviewItemBinding.inflate(layoutInflater, parent, false))

            PARENT_REVIEW_TYPE -> ParentReviewVH(CommentReviewItemBinding.inflate(layoutInflater, parent, false))

            CHILD_REVIEW_TYPE -> ChildReviewVH(CommentReviewItemBinding.inflate(layoutInflater, parent, false))

            LOAD_MORE_TYPE -> LoadMoreVH(BaseLoadMoreItemBinding.inflate(layoutInflater, parent, false))

            SEE_MORE_REVIEW -> SeeMoreVH(SeeMoreReviewItemBinding.inflate(layoutInflater, parent, false))

            else -> LoadMoreVH(BaseLoadMoreItemBinding.inflate(layoutInflater, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {

            is SliderProductVH -> holder.onBind(getItem(position) as List<String>)

            is InfoProductVH -> holder.onBind(getItem(position) as PostReview)

            is CountReviewVH -> holder.onBind(getItem(position) as Int)

            is ParentReviewVH -> holder.onBind(getItem(position) as CommentReviewDisplay)

            is ChildReviewVH -> holder.onBind(getItem(position) as CommentReviewDisplay)

            is SeeMoreVH -> holder.onBind(getItem(position) as Map<String, Int>)
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
            }
        }
    }

    inner class SliderProductVH(private val binding: SliderReviewImageItemBinding) : RecyclerView.ViewHolder(binding.root) {

        private val sliderImageAdapter = SliderImageAdapter()
        private val pagerSnapHelper = PagerSnapHelper()

        init {
            binding.rvSliderReviewImage.adapter = sliderImageAdapter
            binding.rvSliderReviewImage.layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
        }

        fun onBind(data: List<String>) {

            if (isDisableScroll) {
                sliderImageAdapter.submitList(data)

                pagerSnapHelper.attachToRecyclerView(binding.rvSliderReviewImage)
                binding.ciSliderReviewImage.attachToRecyclerView(binding.rvSliderReviewImage, pagerSnapHelper)
                isDisableScroll = false
            }
        }
    }

    inner class InfoProductVH(private val binding: InfoReviewItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(data: PostReview) {
            binding.tvInfoReviewTitle.text = data.getTitleProductsDisplay()
            binding.tvInfoReviewDescription.text = data.getDescriptionProduct()
        }
    }

    inner class RelativeProductVH(private val binding: RelativeProductItemBinding) : RecyclerView.ViewHolder(binding.root)

    inner class CountReviewVH(private val binding: CountReviewItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: Int) {
            binding.tvCountReview.text = getAppString(R.string.count_review, data)
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
                    // listener?.onReplyComment(item.getCommentProduct())
                }
            }
        }

        fun onBind(data: CommentReviewDisplay) {
            bindData(data)
        }

        fun onBind(data: CommentReviewDisplay, payloads: MutableList<Any>) {
            (payloads.firstOrNull() as? List<*>)?.forEach {
                when (it) {
                    UPDATE_STATE_LIKE_REVIEW_PAYLOAD -> {
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
