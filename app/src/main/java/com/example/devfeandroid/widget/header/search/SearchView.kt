package com.example.devfeandroid.widget.header.search

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import com.example.devfeandroid.R
import com.example.devfeandroid.extensions.setOnSafeClick

class SearchView constructor(
    ctx: Context,
    attrs: AttributeSet
) : FrameLayout(ctx, attrs) {

    /**
     * search
     */
    private var ivSearch: ImageView? = null
    private var onClickSearch: ((query: String) -> Unit)? = null

    /**
     * query
     */
    private var edtSearch: EditText? = null

    init {
        LayoutInflater.from(ctx).inflate(R.layout.search_view_layout, this, true)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        ivSearch = findViewById(R.id.ivSearchView)
        edtSearch = findViewById(R.id.edtSearchView)

        ivSearch?.setOnSafeClick {
            onClickSearch?.invoke(edtSearch?.text.toString().trim())
        }
    }

    fun setEventSearch(onClick: (query: String) -> Unit) {
        this.onClickSearch = onClick
    }
}
