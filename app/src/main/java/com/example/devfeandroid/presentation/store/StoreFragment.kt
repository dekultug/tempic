package com.example.devfeandroid.presentation.store

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.devfeandroid.databinding.StoreFragmentBinding
import com.example.devfeandroid.presentation.BaseFragment

class StoreFragment : BaseFragment() {

    private var binding: StoreFragmentBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = StoreFragmentBinding.inflate(inflater, container, false)
        return binding!!.root
    }
}
