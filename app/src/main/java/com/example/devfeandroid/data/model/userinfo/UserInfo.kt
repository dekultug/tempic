package com.example.devfeandroid.data.model.userinfo

import android.os.Parcelable
import com.example.devfeandroid.extensions.BOOLEAN_DEFAULT
import com.example.devfeandroid.extensions.STRING_DEFAULT
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserInfo(
    var id: String? = null,

    var imageUser: String? = null,

    var nameUser: String? = null,

    var follow: Boolean? = null
) : Parcelable {
    fun isFollow(): Boolean {
        return follow ?: BOOLEAN_DEFAULT
    }

    fun getUserId(): String {
        return id ?: STRING_DEFAULT
    }

    fun getUserName(): String {
        return nameUser ?: STRING_DEFAULT
    }

    fun getUserImage(): String {
        return imageUser ?: STRING_DEFAULT
    }
}
