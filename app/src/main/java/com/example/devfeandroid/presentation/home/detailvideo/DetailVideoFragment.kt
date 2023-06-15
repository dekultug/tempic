package com.example.devfeandroid.presentation.home.detailvideo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.devfeandroid.databinding.DetailVideoFragmentBinding
import com.example.devfeandroid.presentation.BaseFragment

class DetailVideoFragment : BaseFragment() {

    private var binding: DetailVideoFragmentBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DetailVideoFragmentBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}
