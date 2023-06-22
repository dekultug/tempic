package com.example.devfeandroid.data.model.review

import android.os.Parcelable
import com.example.devfeandroid.data.model.userinfo.UserInfo
import com.example.devfeandroid.extensions.BOOLEAN_DEFAULT
import com.example.devfeandroid.extensions.INT_DEFAULT
import com.example.devfeandroid.extensions.STRING_DEFAULT
import kotlinx.parcelize.Parcelize

@Parcelize
data class CommentProduct(
    var commentId: String? = null,

    var parentId: String? = null,

    var userInfo: UserInfo? = null,

    var content: String? = null,

    var timePost: String? = null,

    var childComment: List<CommentProduct>? = null,

    var countLike: Int? = null,

    var isMyLike: Boolean? = null
) : Parcelable {
    fun getIdComment(): String {
        return commentId ?: STRING_DEFAULT
    }

    fun getIdParent(): String {
        return parentId ?: STRING_DEFAULT
    }

    fun getInfoUser(): UserInfo {
        return userInfo ?: throw Exception("ko tìm thấy user")
    }

    fun getContentComment(): String {
        return content ?: STRING_DEFAULT
    }

    fun getTimePostComment(): String {
        return timePost ?: STRING_DEFAULT
    }

    fun getCountLike(): Int {
        return countLike ?: INT_DEFAULT
    }

    fun checkMyLike(): Boolean {
        return isMyLike ?: BOOLEAN_DEFAULT
    }

    fun getListReplyComment(): List<CommentProduct> {
        return childComment ?: arrayListOf()
    }
}
