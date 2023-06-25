package com.example.devfeandroid.presentation.store

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.devfeandroid.databinding.StoreFragmentBinding
import com.example.devfeandroid.presentation.BaseFragment

class StoreFragment : BaseFragment() {

    private var binding: StoreFragmentBinding? = null

    private val TAG = "StoreFragment"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = StoreFragmentBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: ")
    }
}
