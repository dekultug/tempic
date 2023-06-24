package com.example.devfeandroid.presentation.widget.saleview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.example.devfeandroid.R

class SaleView constructor(
    ctx: Context,
    attrs: AttributeSet?
) : FrameLayout(ctx, attrs) {

    init {
        LayoutInflater.from(ctx).inflate(R.layout.sale_view_layout, this, true)
    }

}
