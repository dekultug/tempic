package com.example.devfeandroid.widget.filter

import com.example.devfeandroid.R
import com.example.devfeandroid.extensions.getAppString
import com.example.devfeandroid.widget.filter.data.FILTER_TYPE
import com.example.devfeandroid.widget.filter.data.FilterData

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
            else -> arrayListOf<FilterData>()
        }
        return list
    }
}
