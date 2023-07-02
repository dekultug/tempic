package com.example.devfeandroid.presentation.store.serice

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.GridLayoutManager
import com.example.devfeandroid.databinding.PageServiceFragmentBinding
import com.example.devfeandroid.presentation.BaseFragment

class PageServiceFragment : BaseFragment() {

    companion object {
        const val LIST_SERVICE_KEY = "LIST_SERVICE_KEY"
    }

    private var binding: PageServiceFragmentBinding? = null

    private var listData: List<ServiceDisplay>? = null

    private val adapter = ServiceAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PageServiceFragmentBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        receiverData()
        setEventView()
    }

    private fun receiverData() {
        listData = arguments?.getParcelable(LIST_SERVICE_KEY)
    }

    private fun setEventView() {
        binding!!.rvPageService.adapter = this.adapter
        binding!!.rvPageService.layoutManager = GridLayoutManager(context,2)

        adapter.submitList(listData)
    }
}