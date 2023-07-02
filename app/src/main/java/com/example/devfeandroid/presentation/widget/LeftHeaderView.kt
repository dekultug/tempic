package com.example.devfeandroid.presentation.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import com.example.devfeandroid.R
import com.example.devfeandroid.extensions.gone
import com.example.devfeandroid.extensions.setOnSafeClick
import com.example.devfeandroid.extensions.show
import com.example.devfeandroid.presentation.widget.header.search.SearchView

class LeftHeaderView constructor(
    ctx: Context,
    attrs: AttributeSet?
) : FrameLayout(ctx, attrs) {

    private var ivSearch: ImageView? = null

    private var svActionSearch: SearchView? = null

    private var isShowAction: Boolean = false

    init {
        LayoutInflater.from(ctx).inflate(R.layout.left_header_view_layout, this, true)
        initView(attrs)
    }

    fun query(query: (String) -> Unit) {
        svActionSearch?.setEventSearch {
            query.invoke(it)
        }
    }

    private fun initView(attrs: AttributeSet?) {

        ivSearch = findViewById(R.id.ivLeftHeaderSearch)
        svActionSearch = findViewById(R.id.svLeftHeaderActionSearch)

        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.LeftHeaderView, 0, 0)

        if (typedArray.hasValue(R.styleable.LeftHeaderView_lhv_show_action)) {
            isShowAction = typedArray.getBoolean(R.styleable.LeftHeaderView_lhv_show_action, false)
        }
        Log.d("tuglv", "initView: ${isShowAction}")
        if (isShowAction) {
            svActionSearch?.show()
            ivSearch?.gone()
        } else {
            svActionSearch?.gone()
            ivSearch?.show()
        }
    }
}
