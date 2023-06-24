package com.example.devfeandroid.presentation.widget.filter

import com.example.devfeandroid.R
import com.example.devfeandroid.extensions.getAppString
import com.example.devfeandroid.presentation.widget.filter.data.FILTER_TYPE
import com.example.devfeandroid.presentation.widget.filter.data.FilterData

class FilterDisplay : IFilterDisplay {
    override fun getListFilter(type: FILTER_TYPE): List<FilterData> {
        val list: MutableList<FilterData> = arrayListOf()
        when (type) {
            FILTER_TYPE.HOME -> {
                list.add(FilterData(title = getAppString(R.string.all), isSelect = true))
                list.add(FilterData(title = getAppString(R.string.hot), isSelect = false))
                list.add(FilterData(title = getAppString(R.string.now), isSelect = false))
                list.add(FilterData(title = getAppString(R.string.follow), isSelect = false))
            }

            FILTER_TYPE.STORE -> {
                list.add(FilterData(title = getAppString(R.string.home), isSelect = true))
                list.add(FilterData(title = getAppString(R.string.category), isSelect = false))
                list.add(FilterData(title = getAppString(R.string.review), isSelect = false))
                list.add(FilterData(title = getAppString(R.string.theme), isSelect = false))
                list.add(FilterData(title = getAppString(R.string.brand), isSelect = false))
                list.add(FilterData(title = getAppString(R.string.other1), isSelect = false))
                list.add(FilterData(title = getAppString(R.string.other2), isSelect = false))
                list.add(FilterData(title = getAppString(R.string.other3), isSelect = false))
                list.add(FilterData(title = getAppString(R.string.other4), isSelect = false))
            }

            else -> arrayListOf<FilterData>()
        }
        return list
    }
}
