package com.example.devfeandroid.data.model.postreview

import android.os.Parcelable
import com.example.devfeandroid.R
import com.example.devfeandroid.data.model.userinfo.UserInfo
import com.example.devfeandroid.extensions.INT_DEFAULT
import com.example.devfeandroid.extensions.STRING_DEFAULT
import com.example.devfeandroid.extensions.getAppString
import kotlinx.parcelize.Parcelize

@Parcelize
data class PostReview(
    var id: String? = null,

    var imageProduct: List<String>? = null,

    var titleProduct: String? = null,

    var userInfo: UserInfo? = null,

    var numberHeart: Int? = null,

    var urlVideo: String? = null,

    var homeFilter: HOME_FILTER? = null,

    var timePost: String = "2022-05-31 13:59",

    var description: String = getAppString(R.string.test_text_long)
) : Parcelable {

    fun getProductId(): String {
        return id ?: STRING_DEFAULT
    }

    @JvmName("getUserInfo1")
    fun getUserInfo(): UserInfo {
        return userInfo ?: throw Exception("không tìm thấy userinfo")
    }

    fun getImageProductsDisplay(): String {
        return if (imageProduct != null) {
            val index = (0 until imageProduct!!.size).random()
            imageProduct!![index]
        } else {
            STRING_DEFAULT
        }
    }

    fun getTitleProductsDisplay(): String {
        return titleProduct ?: STRING_DEFAULT
    }

    fun getCountNumberHeart(): Int {
        return numberHeart ?: INT_DEFAULT
    }

    fun isHasVideo(): Boolean {
        return urlVideo != null
    }

    fun getAvatarUserInfo(): String {
        return getUserInfo().imageUser ?: STRING_DEFAULT
    }

    fun getUserName(): String {
        return getUserInfo().nameUser ?: STRING_DEFAULT
    }

    fun getDescriptionProduct(): String {
        return description
    }

    fun getTimePostProduct(): String {
        return timePost
    }

    fun getAllImage(): List<String> {
        return imageProduct ?: arrayListOf()
    }
}
