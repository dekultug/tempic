package com.example.devfeandroid.widget.followview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.devfeandroid.R
import com.example.devfeandroid.extensions.*

class FollowView constructor(
    ctx: Context,
    attrs: AttributeSet?
) : FrameLayout(ctx, attrs) {

    private var llFollowRoot: LinearLayout? = null
    private var ivFollow: ImageView? = null
    private var tvFollow: TextView? = null
    private var onFollow: ((Boolean) -> Unit)? = null
    private var isFollow: Boolean = false

    init {
        LayoutInflater.from(ctx).inflate(R.layout.follow_view_layout, this, true)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        llFollowRoot = findViewById(R.id.llFollowRoot)
        ivFollow = findViewById(R.id.ivFollow)
        tvFollow = findViewById(R.id.tvFollow)

        llFollowRoot?.setOnSafeClick {
            isFollow = !isFollow
            isFollow(isFollow)
            onFollow?.invoke(isFollow)
        }
    }

    fun isFollow(isFollow: Boolean) {
        this@FollowView.isFollow = isFollow
        if (isFollow) {
            llFollowRoot?.background = getAppDrawable(R.drawable.shape_bg_white_stroke_corner_32)
            tvFollow?.text = getAppString(R.string.follow_user)
            tvFollow?.textColorMain()
            ivFollow?.setImageResource(R.drawable.ic_followed)
        } else {
            llFollowRoot?.background = getAppDrawable(R.drawable.shape_bg_main_color_corner_32)
            tvFollow?.text = getAppString(R.string.un_follow_user)
            tvFollow?.textColor(R.color.white)
            ivFollow?.setImageResource(R.drawable.ic_add)
        }
    }

    fun setFollow(follow: ((Boolean) -> Unit)? = null) {
        this.onFollow = follow
    }
}
