package com.example.devfeandroid.presentation

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider

open class BaseFragment : Fragment() {
    var mainActivity: MainActivity? = null

    val mainViewModel by lazy {
        ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initVariable()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnBackPressFragment()
    }

    open fun onBackPressFragment() {
        parentFragmentManager.popBackStack()
    }


    private fun initVariable() {
        mainActivity = requireActivity() as MainActivity
        mainActivity!!.showBottomNav(true)
    }

    private fun setOnBackPressFragment() {
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true /* enabled by default */) {
            override fun handleOnBackPressed() {
                onBackPressFragment()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    fun replaceFragment(
        @IdRes containerID: Int,
        fragment: Fragment,
        keepToBackStack: Boolean = true,
        bundle: Bundle? = null
    ) {
        val tag = fragment::class.java.simpleName
        val findFragment = parentFragmentManager.findFragmentByTag(tag)
        if (findFragment != null) {
            parentFragmentManager.popBackStack(tag, 0)
        } else {
            val fm = parentFragmentManager.beginTransaction()
            fm.apply {
                replace(containerID, fragment, tag)
                if (keepToBackStack) {
                    addToBackStack(tag)
                }
                if (bundle != null) {
                    fragment.arguments = bundle
                }
                commit()
            }
        }
    }

    fun replaceFragmentInFragment(
        @IdRes containerID: Int,
        fragment: Fragment,
        keepToBackStack: Boolean = true,
        bundle: Bundle? = null
    ) {
        val tag = fragment::class.java.simpleName
        val findFragment = childFragmentManager.findFragmentByTag(tag)
        if (findFragment != null) {
            childFragmentManager.popBackStack(tag, 0)
        } else {
            val fm = childFragmentManager.beginTransaction()
            fm.apply {
                replace(containerID, fragment, tag)
                if (keepToBackStack) {
                    addToBackStack(tag)
                }
                if (bundle != null) {
                    fragment.arguments = bundle
                }
                commit()
            }
        }
    }
}
