package com.example.devfeandroid.data.model.producthome

import com.example.devfeandroid.data.model.userinfo.UserInfo
import com.example.devfeandroid.extensions.INT_DEFAULT
import com.example.devfeandroid.extensions.STRING_DEFAULT

data class Products(
    var id: String? = null,

    var imageProduct: String? = null,

    var titleProduct: String? = null,

    var userInfo: UserInfo? = null,

    var numberHeart: Int? = null,

    var urlVideo: String? = null,

    var homeFilter: HOME_FILTER? = null
) {
    fun getImageProductsDisplay() = imageProduct ?: STRING_DEFAULT

    fun getTitleProductsDisplay() = titleProduct ?: STRING_DEFAULT

    fun getCountNumberHeart() = numberHeart ?: INT_DEFAULT

    fun isHasVideo() = urlVideo != null

    fun getAvatarUserInfo() = userInfo?.imageUser ?: STRING_DEFAULT

    fun getUserName() = userInfo?.nameUser ?: STRING_DEFAULT
}
