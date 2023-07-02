package com.example.devfeandroid.presentation.home.review.generic

import android.os.Parcelable
import com.example.devfeandroid.data.model.postreview.review.CommentPost
import kotlinx.parcelize.Parcelize
import java.lang.Exception

@Parcelize
data class CommentReviewDisplay(
    var data: CommentPost? = null,

    var isSeeReplyComment: Boolean? = null,

    ) : Parcelable {
    fun getCommentProduct(): CommentPost {
        return data ?: throw Exception("ko có dữ liệu của comment")
    }
}
