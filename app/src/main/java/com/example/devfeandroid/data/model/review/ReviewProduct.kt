package com.example.devfeandroid.data.model.review

import android.os.Parcelable
import com.example.devfeandroid.extensions.INT_DEFAULT
import kotlinx.parcelize.Parcelize

@Parcelize
data class ReviewProduct(
    var totalComment: Int? = null,

    var listComment: List<CommentProduct>? = null
) : Parcelable {
    fun getTotalReview(): Int {
        return totalComment ?: INT_DEFAULT
    }
}
