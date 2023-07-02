package com.example.devfeandroid.data.model.postreview.review

import android.os.Parcelable
import com.example.devfeandroid.extensions.INT_DEFAULT
import kotlinx.parcelize.Parcelize

@Parcelize
data class ReviewPost(
    var totalComment: Int? = null,

    var listComment: List<CommentPost>? = null
) : Parcelable {
    fun getTotalReview(): Int {
        return totalComment ?: INT_DEFAULT
    }
}
