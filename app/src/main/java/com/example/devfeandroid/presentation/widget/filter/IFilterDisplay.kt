package com.example.devfeandroid.presentation.widget.filter

import com.example.devfeandroid.presentation.widget.filter.data.FILTER_TYPE
import com.example.devfeandroid.presentation.widget.filter.data.FilterData

interface IFilterDisplay {
    fun getListFilter(type: FILTER_TYPE): List<FilterData>
}
