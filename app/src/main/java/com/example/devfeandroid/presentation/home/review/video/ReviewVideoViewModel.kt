package com.example.devfeandroid.presentation.home.review.video

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.devfeandroid.data.model.postreview.PostReview
import com.example.devfeandroid.data.model.postreview.review.CommentPost
import com.example.devfeandroid.data.model.userinfo.UserInfo
import com.example.devfeandroid.data.repo.Repository
import com.example.devfeandroid.extensions.INT_DEFAULT
import com.example.devfeandroid.presentation.home.review.generic.COMMENT_REVIEW_TYPE
import com.example.devfeandroid.presentation.home.review.generic.ITEM_REVIEW_TYPE
import com.example.devfeandroid.presentation.state.StateData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class ReviewVideoViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {

    private val _product = savedStateHandle.get<PostReview>(ReviewVideoFragment.REVIEW_PRODUCT_KEY)
    val product = MutableStateFlow(PostReview())

    private var _commentReviewState = MutableStateFlow<StateData<List<Any>>>(StateData.Init())
    val commentReviewState = _commentReviewState.asStateFlow()

    var totalReview: Int = INT_DEFAULT

    /**
     * list data
     */
    val list: MutableList<Any> = arrayListOf()

    /**
     * page để chặn load more
     */
    var page = 0

    /**
     * lưu lại trạng thái để map với model display
     */
    var oldStateSeeReplyCommentReview: MutableMap<String, Boolean> = HashMap()

    /**
     * trạng thái create comment
     */
    var actionReviewType: COMMENT_REVIEW_TYPE = COMMENT_REVIEW_TYPE.COMMENT_REVIEW

    /**
     * tạo 1 comment mới
     */
    var commentPost: CommentPost? = null

    var myUserInfo = UserInfo(
        id = "tunglv2002",
        imageUser = "https://scontent.fhan2-5.fna.fbcdn.net/v/t39.30808-6/326250893_1637024063411506_2452918235866757450_n.jpg?_nc_cat=109&ccb=1-7&_nc_sid=e3f864&_nc_ohc=31G5HyXxM8YAX9vm06d&_nc_ht=scontent.fhan2-5.fna&oh=00_AfC5MPsGjRSwkO0iBOms9aJ7Lqpzrcgo4J2fx_WbS6pQKw&oe=64988311",
        nameUser = "Lưu Tùng"
    )

    var indexScroll = -1

    init {
        if (_product != null) {
            product.value = _product
        }
        getListReview()
    }

    fun resetData() {
        actionReviewType = COMMENT_REVIEW_TYPE.COMMENT_REVIEW
        commentPost = null
    }

    fun getListReview(isLoadMore: Boolean = false) {
        viewModelScope.launch {

            val repo = Repository.getProductRepo()

            repo.getListReviewProducts(true).onStart {
                if (isLoadMore) {
                    page++
                    list.add(ITEM_REVIEW_TYPE.LOAD_MORE)
                    _commentReviewState.value = StateData.Success(data = list)
                    delay(1000)
                } else {
                    _commentReviewState.value = StateData.Loading()
                    oldStateSeeReplyCommentReview.clear()
                }
            }.catch {
                _commentReviewState.value = StateData.Error(throwable = it)
            }.collect {

                if (isLoadMore) {
                    val index = list.indexOfFirst {
                        it == ITEM_REVIEW_TYPE.LOAD_MORE
                    }
                    if (index >= 0) {
                        list.removeAt(index)
                    }
                }

                totalReview = it.getTotalReview()
                if (it.listComment != null) {
                    list.addAll(it.listComment!!)
                }
                _commentReviewState.value = StateData.Success(data = list)
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
                        _commentReviewState.value = StateData.Success(list)
                        delay(500)
                    }
                }
            }.catch {
                _commentReviewState.value = StateData.Error(throwable = it)
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

                    _commentReviewState.value = StateData.Success(data = list)
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

                _commentReviewState.value = StateData.Success(data = list.toMutableList())
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
                _commentReviewState.value = StateData.Success(data = list.toMutableList())
            }
        }
    }

    fun createComment(content: String) {
        viewModelScope.launch(Dispatchers.IO) {

            val newComment = commentPost!!.copy()
            newComment.content = content

            when (actionReviewType) {
                COMMENT_REVIEW_TYPE.COMMENT_REVIEW -> {
                    list.add(0, newComment)
                    indexScroll = 0
                }

                COMMENT_REVIEW_TYPE.REPLY_COMMENT -> {

                    val indexCommentParent = list.indexOfFirst {
                        newComment.getIdParent() == (it as? CommentPost)?.getIdComment()
                    }

                    if (indexCommentParent in 0 until list.size) {
                        var size = 0
                        list.forEach {
                            if ((it as? CommentPost)?.getIdParent() == newComment.getIdParent()) {
                                size++
                            }
                        }
                        indexScroll = if (size == 0) {
                            list.add(indexCommentParent + 1, newComment)
                            indexCommentParent + 1
                        } else {
                            /**
                             *cộng thêm phần tử của type xem thêm
                             */
                            list.add(size + indexCommentParent + 2, newComment)
                            indexCommentParent + size + 2
                        }

                        /**
                         * update comment
                         */
                        val newItem = (list[indexCommentParent] as CommentPost).copy()
                        newItem.childComment?.toMutableList()?.add(newComment)
                        list[indexCommentParent] = newItem
                    }
                }
            }
            _commentReviewState.value = StateData.Success(list)
        }
    }
}
