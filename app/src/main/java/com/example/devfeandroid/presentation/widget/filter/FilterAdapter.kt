package com.example.devfeandroid.presentation.widget.filter

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.devfeandroid.R
import com.example.devfeandroid.databinding.FilterItemBinding
import com.example.devfeandroid.extensions.*
import com.example.devfeandroid.presentation.widget.filter.data.FilterData


class FilterAdapter : RecyclerView.Adapter<FilterAdapter.FilterViewHolder>() {

    companion object {
        private const val CHANGE_STATE_PAYLOAD = "CHANGE_STATE_PAYLOAD"
        private const val TAG = "FilterAdapter"
    }

    private var dataList: MutableList<FilterData> = arrayListOf()

    private var minWidth: Int = 0

    private var fontTextItemSelect: Typeface? = null

    private var fontTextItemUnSelect: Typeface? = null

    var listener: IFilterListener? = null

    fun addContentListFilter(list: List<FilterData>) {
        dataList.clear()
        dataList.addAll(list)
        notifyDataSetChanged()
    }

    fun selectItemOld(position: Int) {
        selectItem(position, false)
    }

    private fun selectItem(position: Int, isHasCallBack: Boolean = true) {
        val oldPosition = dataList.indexOfFirst {
            it.isSelect == true
        }
        if (oldPosition != position) {
            dataList[oldPosition].isSelect = false
            notifyItemChanged(oldPosition, CHANGE_STATE_PAYLOAD)
            dataList[position].isSelect = true
            notifyItemChanged(position, CHANGE_STATE_PAYLOAD)

            if (isHasCallBack) {
                listener?.onSelect(dataList[position], position)
            }
        }
    }

    fun setMinWidth(value: Int) {
        this.minWidth = value
    }

    fun setFontFamilySelectItem(typeface: Typeface?) {
        fontTextItemSelect = typeface
    }

    fun setFontFamilyUnSelectItem(typeface: Typeface?) {
        fontTextItemUnSelect = typeface
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {

        return FilterViewHolder(FilterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        holder.onBind(dataList[position])
    }

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
        } else {
            holder.onBind(dataList[position], payloads)
        }
    }

    override fun getItemCount() = dataList.size

    inner class FilterViewHolder(private val binding: FilterItemBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnSafeClick {
                selectItem(absoluteAdapterPosition)
            }
        }

        fun onBind(data: FilterData) {
            binding.flFilterRoot.minimumWidth = this@FilterAdapter.minWidth
            binding.tvFilterTitle.text = data.title
            stateSelect(data)
        }

        fun onBind(data: FilterData, payloads: List<Any>) {
            payloads.forEach {
                when (it) {
                    CHANGE_STATE_PAYLOAD -> stateSelect(data)
                }
            }
        }

        private fun stateSelect(data: FilterData) {
            if (data.isSelect == true) {
                binding.vFilterSelect.show()
                binding.tvFilterTitle.textColorMain()
                binding.tvFilterTitle.typeface = fontTextItemSelect
                    ?: getAppFont(R.font.notosans_medium)
            } else {
                binding.vFilterSelect.gone()
                binding.tvFilterTitle.typeface = fontTextItemUnSelect
                    ?: getAppFont(R.font.notosans_regular)
                binding.tvFilterTitle.textColor(R.color.gray_light)
            }
        }
    }

    interface IFilterListener {
        fun onSelect(filterDisplay: FilterData, position: Int)
    }
}
