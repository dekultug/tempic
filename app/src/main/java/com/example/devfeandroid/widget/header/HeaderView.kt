package com.example.devfeandroid.widget.header

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.devfeandroid.R
import com.example.devfeandroid.widget.filter.FilterView
import com.example.devfeandroid.widget.header.search.SearchView

class HeaderView constructor(
    ctx: Context,
    attrs: AttributeSet?
) : FrameLayout(ctx, attrs) {

    /**
     * left
     */
    private var llLeftRoot: LinearLayout? = null
    private var ivLeft: ImageView? = null
    private var tvLeft: TextView? = null

    /**
     * center
     */
    private var flCenterRoot: FrameLayout? = null
    private var fvCenter: FilterView? = null
    private var svCenter: SearchView? = null

    /**
     * right
     */
    private var clRightRoot: ConstraintLayout? = null
    private var ivRightOne: ImageView? = null
    private var ivRightTwo: ImageView? = null
    private var ivRightThree: ImageView? = null


    init {
        LayoutInflater.from(ctx).inflate(R.layout.header_view_layout, this, false)
        initAttrs(attrs)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        // left
        llLeftRoot = findViewById(R.id.llHeaderViewLeft)
        ivLeft = findViewById(R.id.ivHeadViewLeft)
        tvLeft = findViewById(R.id.tvHeaderViewLeft)

        //center
        flCenterRoot = findViewById(R.id.flHeaderViewCenter)
        fvCenter = findViewById(R.id.fvHeaderViewCenter)
        svCenter = findViewById(R.id.svHeaderView)

        // right
        clRightRoot = findViewById(R.id.clHeaderViewRight)
        ivRightOne = findViewById(R.id.ivHeaderViewRightOne)
        ivRightTwo = findViewById(R.id.ivHeaderViewRightTwo)
        ivRightThree = findViewById(R.id.ivHeaderRightThree)
    }

    private fun initAttrs(attrs: AttributeSet?) {
//        val ta = context.theme.obtainStyledAttributes(
//            attrs,
//            R.styleable.LeftRightTextView, 0, 0
//        )
    }
}
