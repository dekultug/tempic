package com.example.devfeandroid.presentation.home.review.image

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.devfeandroid.data.model.postreview.PostReview
import com.example.devfeandroid.data.model.postreview.review.CommentPost
import com.example.devfeandroid.data.repo.Repository
import com.example.devfeandroid.extensions.INT_DEFAULT
import com.example.devfeandroid.presentation.home.review.generic.ITEM_REVIEW_TYPE
import com.example.devfeandroid.presentation.state.StateData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class ReviewImageViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {

    val product = savedStateHandle.get<PostReview>(ReviewImageFragment.REVIEW_IMAGE_PRODUCT_KEY)

    private var _reviewState = MutableStateFlow<StateData<List<Any>>>(StateData.Init())
    val reviewState = _reviewState.asStateFlow()

    val list: MutableList<Any> = arrayListOf()

    var page = 0

    var oldStateSeeReplyCommentReview: MutableMap<String, Boolean> = HashMap()

    init {
        getInfoReviewProduct(true)
    }

    fun getInfoReviewProduct(isReLoad: Boolean = false) {
        if (isReLoad) {
            list.clear()
            page = 0
        }

        product?.let {
            list.add(it.getAllImage())
            list.add(it)
        }
        list.add(REVIEW_IMAGE_TYPE.RELATIVE_PRODUCT)
        getListReview()
    }

    fun getListReview(isLoadMore: Boolean = false) {
        viewModelScope.launch {
            val repo = Repository.getProductRepo()
            repo.getListReviewProducts()
                .onStart {
                    if (isLoadMore) {
                        page++
                        list.add(REVIEW_IMAGE_TYPE.LOAD_MORE)
                        _reviewState.value = StateData.Success(data = list)
                        delay(1000)
                    } else {
                        _reviewState.value = StateData.Loading()
                    }
                }
                .catch {
                    _reviewState.value = StateData.Error(throwable = it)
                }
                .collect {

                    if (isLoadMore) {
                        val index = list.indexOfFirst {
                            it == REVIEW_IMAGE_TYPE.LOAD_MORE
                        }
                        if (index >= 0) {
                            list.removeAt(index)
                        }
                    } else {
                        list.add(it.totalComment ?: INT_DEFAULT)
                    }

                    list.addAll(it.listComment ?: arrayListOf())
                    _reviewState.value = StateData.Success(list)
                }
        }
    }

    fun getListChildReview(commentId: String, isSeeMore: Boolean = false) {
        viewModelScope.launch {
            val repo = Repository.getProductRepo()
            repo.getListChildReview(commentId).onStart {
                if (isSeeMore) {
                    /**
                     * remove type: xem câu trả lời
                     */
                    var index = list.indexOfFirst {
                        it is Map<*, *> && it.containsKey(commentId)
                    }
                    if (index in 0 until list.size) {
                        list.removeAt(index)
                        list.add(index, ITEM_REVIEW_TYPE.LOAD_MORE)
                        _reviewState.value = StateData.Success(list)
                        delay(500)
                    }
                }
            }.catch {
                _reviewState.value = StateData.Error(throwable = it)
            }.collect {

                if (isSeeMore) {
                    val indexLoadMore = list.indexOfFirst {
                        it == ITEM_REVIEW_TYPE.LOAD_MORE
                    }
                    if (indexLoadMore >= 0) {
                        list.removeAt(indexLoadMore)
                    }
                }

                val index = list.indexOfFirst {
                    (it as? CommentPost)?.commentId == commentId
                }

                if (index in 0 until list.size) {
                    oldStateSeeReplyCommentReview[commentId] = true

                    /**
                     * update list child
                     */
                    val newItem = (list[index] as? CommentPost)?.copy()
                    val newListChild = newItem?.childComment?.toMutableList()
                    newListChild?.addAll(it)
                    newItem?.childComment = newListChild
                    if (newItem != null) {
                        list[index] = newItem
                    }

                    val map: MutableMap<String, Int> = HashMap()
                    map[commentId] = it.size
                    list.add(index + 1, map)
                    if (index in 0 until list.size) {
                        list.addAll(index + 2, it)
                    }

                    _reviewState.value = StateData.Success(data = list)
                }
            }
        }
    }

    fun removeListChildReview(commentId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val index = list.indexOfFirst {
                (it as? CommentPost)?.commentId == commentId
            }

            if (index in 0 until list.size) {

                list.removeIf {
                    (it as? CommentPost)?.parentId == commentId || (it as? Map<*, *>)?.containsKey(commentId) == true
                }
                oldStateSeeReplyCommentReview[commentId] = false

                val newItem = (list[index] as CommentPost).copy()

                newItem.childComment = arrayListOf()
                list[index] = newItem.copy()

                _reviewState.value = StateData.Success(data = list.toMutableList())
            }
        }
    }

    fun interactReview(commentId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val index = list.indexOfFirst {
                (it as? CommentPost)?.commentId == commentId
            }
            if (index in 0 until list.size) {
                val newItem = (list[index] as CommentPost).copy()
                newItem.isMyLike = !newItem.isMyLike!!
                if (newItem.isMyLike == true) {
                    newItem.countLike = newItem.countLike ?: (INT_DEFAULT + 1)
                } else {
                    if (newItem.countLike != null && newItem.countLike!! > 0) {
                        newItem.countLike = newItem.countLike!! - 1
                    }
                }
                list[index] = newItem
                _reviewState.value = StateData.Success(data = list.toMutableList())
            }
        }
    }
}
