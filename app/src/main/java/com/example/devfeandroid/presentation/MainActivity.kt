package com.example.devfeandroid.presentation

import android.os.Bundle
import android.provider.ContactsContract.RawContacts.Data
import androidx.activity.OnBackPressedCallback
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
import com.example.devfeandroid.R
import com.example.devfeandroid.databinding.ActivityMainBinding
import com.example.devfeandroid.extensions.STRING_DEFAULT
import com.example.devfeandroid.extensions.gone
import com.example.devfeandroid.extensions.show
import com.example.devfeandroid.presentation.home.HomeFragment
import com.example.devfeandroid.presentation.splash.SplashFragment
import com.example.devfeandroid.presentation.state.StateData
import com.example.devfeandroid.presentation.store.StoreFragment
import com.example.devfeandroid.widget.bottomnav.BottomBarNavigationView
import com.example.devfeandroid.widget.bottomnav.TAB_BOTTOM_NAV
import java.util.*

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null

    var saveFilterSelect: MutableMap<TAB_BOTTOM_NAV, Int> = EnumMap(TAB_BOTTOM_NAV::class.java)

    /**
     * lifecycle
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        setUpBottomNav()
        showBottomNav(false)
        addFirstFragment()
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

    /**
     * function
     */

    private fun setUpBottomNav() {
        binding!!.bnvMainNavigation.listener = object : BottomBarNavigationView.IBottomBarListener {
            override fun onTabHome() {
                replaceFragment(R.id.flMainContainerFragment, HomeFragment())
            }

            override fun onTabStore() {
                replaceFragment(R.id.flMainContainerFragment, StoreFragment())
            }

            override fun onTabPost() {
//                replaceFragment(R.id.flMainContainerFragment,HomeFragment())
            }

            override fun onTabGift() {
//                replaceFragment(R.id.flMainContainerFragment,HomeFragment())
            }

            override fun onTabPersonal() {
//                replaceFragment(R.id.flMainContainerFragment,HomeFragment())
            }
        }
    }

    private fun addFirstFragment() {
        // TODO: check login sau
        replaceFragment(R.id.flMainContainerFragment, SplashFragment(), false)
    }

    fun showBottomNav(isShown: Boolean) {
        if (isShown) {
            binding!!.bnvMainNavigation.show()
        } else {
            binding!!.bnvMainNavigation.gone()
        }
    }

    fun replaceFragment(@IdRes containerID: Int, fragment: Fragment, keepToBackStack: Boolean = true) {
        val tag = fragment::class.java.simpleName
        val findFragment = supportFragmentManager.findFragmentByTag(tag)
        if (findFragment != null) {
            supportFragmentManager.popBackStack(tag, 0)
        } else {
            val fm = supportFragmentManager.beginTransaction()
            fm.apply {
                replace(containerID, fragment, tag)
                if (keepToBackStack) {
                    addToBackStack(tag)
                }
                commit()
            }
        }
    }

    fun closeApp() {
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true /* enabled by default */) {
            override fun handleOnBackPressed() {
                this@MainActivity.supportFragmentManager.popBackStack(null, POP_BACK_STACK_INCLUSIVE)
                this@MainActivity.finish()
            }
        }
        this.onBackPressedDispatcher.addCallback(this, callback)
    }

    /**
     * state
     */
    fun <Data> setState(
        stateData: StateData<Data>,
        listener: IUIState<Data>
    ) {
        when (stateData) {

            is StateData.Init<Data> -> {
                /**
                 * do thing
                 */
            }

            is StateData.Loading<Data> -> {
                binding!!.llMainLoading.show()
                listener.onLoading()
            }

            is StateData.Error<Data> -> {
                binding!!.llMainLoading.gone()
                listener.onError(stateData.message ?: STRING_DEFAULT, stateData.exception!!)
            }

            is StateData.Success<Data> -> {
                binding!!.llMainLoading.gone()
                stateData.data?.let { listener.onSuccess(it) }
            }
        }
    }

    interface IUIState<Data> {
        fun onLoading() {}
        fun onError(s: String, exception: Exception) {}
        fun onSuccess(data: Data)
    }
}
