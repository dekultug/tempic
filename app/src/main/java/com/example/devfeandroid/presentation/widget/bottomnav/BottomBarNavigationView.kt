package com.example.devfeandroid.presentation.widget.bottomnav

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import com.example.devfeandroid.R
import com.example.devfeandroid.extensions.setOnSafeClick

class BottomBarNavigationView constructor(
    ctx: Context,
    attrs: AttributeSet?
) : FrameLayout(ctx, attrs) {

    /**
     * home
     */
    private var flHome: FrameLayout? = null
    private var ivHome: ImageView? = null

    /**
     * store
     */
    private var flStore: FrameLayout? = null
    private var ivStore: ImageView? = null

    /**
     * post
     */
    private var flPost: FrameLayout? = null
    private var ivPost: ImageView? = null

    /**
     * gift
     */
    private var flGift: FrameLayout? = null
    private var ivGift: ImageView? = null

    /**
     * personal
     */
    private var flPersonal: FrameLayout? = null
    private var ivPersonal: ImageView? = null

    private var currentTab: TAB_BOTTOM_NAV = TAB_BOTTOM_NAV.HOME

    var listener: IBottomBarListener? = null

    init {
        LayoutInflater.from(ctx).inflate(R.layout.bottom_bar_navigation_view_layout, this, true)
        initView(attrs)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        flHome = findViewById(R.id.flBottomBarNavigationViewHome)
        ivHome = findViewById(R.id.ivBottomBarNavigationViewHome)

        flStore = findViewById(R.id.flBottomBarNavigationViewCart)
        ivStore = findViewById(R.id.ivBottomBarNavigationViewCart)

        flPost = findViewById(R.id.flBottomBarNavigationViewPost)
        ivPost = findViewById(R.id.ivBottomBarNavigationViewPost)

        flGift = findViewById(R.id.flBottomBarNavigationViewGift)
        ivGift = findViewById(R.id.ivBottomBarNavigationViewGift)

        flPersonal = findViewById(R.id.flBottomBarNavigationViewPersonal)
        ivPersonal = findViewById(R.id.ivBottomBarNavigationViewPersonal)

        flHome?.setOnSafeClick {
            currentTab = TAB_BOTTOM_NAV.HOME
            setItemSelected(currentTab)
            listener?.onTabHome()
        }

        flStore?.setOnSafeClick {
            currentTab = TAB_BOTTOM_NAV.STORE
            setItemSelected(currentTab)
            listener?.onTabStore()
        }

        flPost?.setOnSafeClick {
            currentTab = TAB_BOTTOM_NAV.POST
            setItemSelected(currentTab)
            listener?.onTabPost()
        }

        flGift?.setOnSafeClick {
            currentTab = TAB_BOTTOM_NAV.GIFT
            setItemSelected(currentTab)
            listener?.onTabGift()
        }

        flPersonal?.setOnSafeClick {
            currentTab = TAB_BOTTOM_NAV.PERSONAL
            setItemSelected(currentTab)
            listener?.onTabPersonal()
        }
    }

    private fun setItemSelected(tab: TAB_BOTTOM_NAV) {
        ivHome?.setImageResource(R.drawable.ic_home)
        ivStore?.setImageResource(R.drawable.ic_cart)
        ivPost?.setImageResource(R.drawable.ic_post)
        ivGift?.setImageResource(R.drawable.ic_gift)
        ivPersonal?.setImageResource(R.drawable.ic_person)

        when (tab) {
            TAB_BOTTOM_NAV.HOME -> {
                ivHome?.setImageResource(R.drawable.ic_nav_home_select)
            }

            TAB_BOTTOM_NAV.STORE -> {
                ivStore?.setImageResource(R.drawable.ic_store_blue)
            }

            TAB_BOTTOM_NAV.POST -> {
                ivPost?.setImageResource(R.drawable.ic_post_select)
            }

            TAB_BOTTOM_NAV.GIFT -> {
                ivGift?.setImageResource(R.drawable.ic_nav_gift_select)
            }

            TAB_BOTTOM_NAV.PERSONAL -> {
                ivPersonal?.setImageResource(R.drawable.ic_person_blue)
            }
        }
    }

    private fun initView(attrs: AttributeSet?) {
    }

    interface IBottomBarListener {
        fun onTabHome()
        fun onTabStore()
        fun onTabPost()
        fun onTabGift()
        fun onTabPersonal()
    }
}
