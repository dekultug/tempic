package com.example.devfeandroid.presentation.home.review.generic

import android.os.Parcelable
import com.example.devfeandroid.data.model.review.CommentProduct
import kotlinx.parcelize.Parcelize
import java.lang.Exception

@Parcelize
data class CommentReviewDisplay(
    var data: CommentProduct? = null,

    var isSeeReplyComment: Boolean? = null,

) : Parcelable {
    fun getCommentProduct(): CommentProduct {
        return data ?: throw Exception("ko có dữ liệu của comment")
    }
}
