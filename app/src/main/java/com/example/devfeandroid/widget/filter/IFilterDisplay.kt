package com.example.devfeandroid.widget.filter

import com.example.devfeandroid.widget.filter.data.FILTER_TYPE
import com.example.devfeandroid.widget.filter.data.FilterData

interface IFilterDisplay {
    fun getListFilter(type: FILTER_TYPE): List<FilterData>
}
