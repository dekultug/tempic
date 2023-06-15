package com.example.devfeandroid.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment

open class BaseFragment: Fragment() {
    var mainActivity: MainActivity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initVariable()
    }

    private fun initVariable() {
        mainActivity = requireActivity() as MainActivity
        mainActivity!!.closeApp()
        mainActivity!!.showBottomNav(true)
    }
}
