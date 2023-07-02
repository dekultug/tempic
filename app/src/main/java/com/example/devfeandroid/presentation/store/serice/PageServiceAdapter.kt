package com.example.devfeandroid.presentation.store.serice

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.devfeandroid.presentation.store.serice.PageServiceFragment.Companion.LIST_SERVICE_KEY

class PageServiceAdapter(
    private val list: List<ServiceDisplay>,
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return list.size / 10
    }

    override fun createFragment(position: Int): Fragment {
        val pageServiceFragment = PageServiceFragment()
        var listData = arrayListOf<ServiceDisplay>()
        listData = if (position == 0) {
            if (list.size >= 10) {
                list.subList(0, 10) as ArrayList<ServiceDisplay>
            } else {
                list as ArrayList<ServiceDisplay>
            }
        } else {
            if (list.size / position >= 10) {
                list.subList(position * 10, position * 10 + 10) as ArrayList<ServiceDisplay>
            } else {
                list.subList(position * 10, list.size) as ArrayList<ServiceDisplay>
            }
        }
        pageServiceFragment.arguments = bundleOf(LIST_SERVICE_KEY to listData)
        return pageServiceFragment
    }
}