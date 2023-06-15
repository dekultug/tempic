package com.example.devfeandroid.widget.filter

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.devfeandroid.R
import com.example.devfeandroid.extensions.STRING_DEFAULT
import com.example.devfeandroid.widget.filter.data.FILTER_TYPE
import com.example.devfeandroid.widget.filter.data.FilterData

class FilterView constructor(
    ctx: Context,
    attrs: AttributeSet?
) : FrameLayout(ctx, attrs) {

    private lateinit var rvList: RecyclerView

    private var filterAdapter: FilterAdapter = FilterAdapter()

    private var currentIndex = 0

    var listener: IFilterViewEvent? = null

    val filterDisplay by lazy {
        FilterDisplay()
    }

    init {
        LayoutInflater.from(ctx).inflate(R.layout.filter_view_layout, this, true)
        initView(attrs)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        rvList = findViewById(R.id.rvFilterView)

        rvList.adapter = this.filterAdapter
        rvList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        filterAdapter.listener = object : FilterAdapter.IFilterListener {
            override fun onSelect(filterDisplay: FilterData, position: Int) {
                listener?.onFilter(filterDisplay.title ?: STRING_DEFAULT, position)
                if (position >= currentIndex) {
                    rvList.smoothScrollToPosition(position + 1)
                } else {
                    if (position >= 1) {
                        rvList.smoothScrollToPosition(position - 1)
                    } else {
                        rvList.smoothScrollToPosition(0)
                    }
                }
                currentIndex = position
            }
        }
    }

    private fun initView(attrs: AttributeSet?) {
        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.FilterView, 0, 0)
        val filterView = typedArray.getInt(R.styleable.FilterView_fv_type, 0)
        FILTER_TYPE.valueOfName(filterView)?.let {
            filterAdapter.addContentListFilter(filterDisplay.getListFilter(it))
        }
    }

    fun selectItem(position: Int) {
        filterAdapter.selectItemOld(position)
    }

    interface IFilterViewEvent {
        fun onFilter(title: String, position: Int)
    }
}
