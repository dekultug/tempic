package com.example.devfeandroid.presentation.widget.filter

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import android.util.Log
import android.util.SparseArray
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.devfeandroid.R
import com.example.devfeandroid.extensions.STRING_DEFAULT
import com.example.devfeandroid.extensions.restoreInstanceState
import com.example.devfeandroid.extensions.saveInstanceState
import com.example.devfeandroid.presentation.widget.filter.data.FILTER_TYPE
import com.example.devfeandroid.presentation.widget.filter.data.FilterData
import java.util.EnumMap

class FilterView constructor(
    ctx: Context,
    attrs: AttributeSet?
) : FrameLayout(ctx, attrs) {

    private val TAG = "FilterView"

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
        Log.d(TAG, "onFinishInflate: ")
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

    override fun onSaveInstanceState(): Parcelable {
        val savedState = super.onSaveInstanceState()
        return ViewState(savedState, currentIndex)
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val viewState = state as? ViewState
        super.onRestoreInstanceState(viewState?.savedState ?: state)
        currentIndex = viewState?.index ?: 0
        filterAdapter.selectItemOld(currentIndex)
    }

    private fun initView(attrs: AttributeSet?) {
        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.FilterView, 0, 0)
        val filterView = typedArray.getInt(R.styleable.FilterView_fv_type, 0)
        FILTER_TYPE.valueOfName(filterView)?.let {
            filterAdapter.addContentListFilter(filterDisplay.getListFilter(it))
        }

        if (typedArray.hasValue(R.styleable.FilterView_fv_min_width)) {
            val value = typedArray.getDimension(R.styleable.FilterView_fv_min_width, 0f)
            filterAdapter.setMinWidth(value.toInt())
        }

        if (typedArray.hasValue(R.styleable.FilterView_fv_font_family_item_select)) {
            val value = typedArray.getFont(R.styleable.FilterView_fv_font_family_item_select)
            filterAdapter.setFontFamilySelectItem(value)
        }

        if (typedArray.hasValue(R.styleable.FilterView_fv_font_family_item_un_select)) {
            val value = typedArray.getFont(R.styleable.FilterView_fv_font_family_item_un_select)
            filterAdapter.setFontFamilyUnSelectItem(value)
        }
    }

    class ViewState(val savedState: Parcelable?, val index: Int?) : BaseSavedState(savedState)

    interface IFilterViewEvent {
        fun onFilter(title: String, position: Int)
    }
}
