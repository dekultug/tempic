package com.example.devfeandroid.presentation.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.devfeandroid.R
import com.example.devfeandroid.databinding.SplashFragmentBinding
import com.example.devfeandroid.presentation.BaseFragment
import com.example.devfeandroid.presentation.home.HomeFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : BaseFragment() {

    private var binding: SplashFragmentBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = SplashFragmentBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity!!.showBottomNav(false)
        lifecycleScope.launch {
            delay(1000)
            mainActivity!!.replaceFragment(R.id.flMainContainerFragment, HomeFragment())
        }
    }
}
