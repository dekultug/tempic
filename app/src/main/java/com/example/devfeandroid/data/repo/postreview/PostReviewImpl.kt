package com.example.devfeandroid.data.repo.postreview

import com.example.devfeandroid.data.mocKImageUser
import com.example.devfeandroid.data.mockImagePostReview
import com.example.devfeandroid.data.mockListCommentProduct
import com.example.devfeandroid.data.mockListTitle
import com.example.devfeandroid.data.mockNameUser
import com.example.devfeandroid.data.model.postreview.HOME_FILTER
import com.example.devfeandroid.data.model.postreview.PostReview
import com.example.devfeandroid.data.model.postreview.review.CommentPost
import com.example.devfeandroid.data.model.postreview.review.ReviewPost
import com.example.devfeandroid.data.model.userinfo.UserInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class PostReviewImpl : IPostReviewRepo {

    private val totalReview = 323

    private val reviewPost = ReviewPost(
        totalComment = totalReview,
        listComment = mockListCommentProduct().subList(0, 20)
    )

    override fun getProductList(type: HOME_FILTER, page: Int): List<PostReview> {

            val list: MutableList<PostReview> = arrayListOf()
            when (type) {
                HOME_FILTER.ALL -> {
                    val start = 20 * page
                    val end = start + 20
                    if (page == 4) {
                        for (i in start..end - 10) {
                            list.add(PostReview(
                                id = "00${i + 1}",
                                imageProduct = mockImagePostReview(),
                                titleProduct = mockListTitle(),
                                userInfo = UserInfo(
                                    id = "10000${i}",
                                    imageUser = mocKImageUser(),
                                    nameUser = mockNameUser()
                                ),
                                numberHeart = (0..1000).random(),
                                homeFilter = type,
                                urlVideo = "https://www.tiktok.com/@tuan.anh.real.1804/video/7238440594777885958?enter_from=video_detail&is_copy_url=1&is_from_webapp=v1"
                            ))
                        }
                    } else {
                        for (i in start until end) {
                            list.add(PostReview(
                                id = "00${i + 1}",
                                imageProduct = mockImagePostReview(),
                                titleProduct = mockListTitle(),
                                userInfo = UserInfo(
                                    id = "10000${i}",
                                    imageUser = mocKImageUser(),
                                    nameUser = mockNameUser()
                                ),
                                numberHeart = (0..1000).random(),
                                homeFilter = type,
                                urlVideo = "https://www.tiktok.com/@tuan.anh.real.1804/video/7238440594777885958?enter_from=video_detail&is_copy_url=1&is_from_webapp=v1"
                            ))
                        }
                    }
                }

                HOME_FILTER.HOT -> {
                    val start = 100 * page
                    val end = start + 20
                    if (page == 7) {
                        for (i in start..end - 10) {
                            list.add(PostReview(
                                id = "00${i + 1}",
                                imageProduct = mockImagePostReview(),
                                titleProduct = mockListTitle(),
                                userInfo = UserInfo(
                                    id = "10000${i}",
                                    imageUser = mocKImageUser(),
                                    nameUser = mockNameUser()
                                ),
                                numberHeart = (0..1000).random(),
                                homeFilter = type
                            ))
                        }
                    } else {
                        for (i in start..end) {
                            list.add(PostReview(
                                id = "00${i + 1}",
                                imageProduct = mockImagePostReview(),
                                titleProduct = mockListTitle(),
                                userInfo = UserInfo(
                                    id = "10000${i}",
                                    imageUser = mocKImageUser(),
                                    nameUser = mockNameUser()
                                ),
                                numberHeart = (0..1000).random(),
                                homeFilter = type
                            ))
                        }
                    }
                }

                HOME_FILTER.NOW -> {
                    val start = 600 * page
                    val end = start + 20
                    if (page == 10) {
                        for (i in start..end - 10) {
                            list.add(PostReview(
                                id = "00${i + 1}",
                                imageProduct = mockImagePostReview(),
                                titleProduct = mockListTitle(),
                                userInfo = UserInfo(
                                    id = "10000${i}",
                                    imageUser = mocKImageUser(),
                                    nameUser = mockNameUser()
                                ),
                                numberHeart = (0..1000).random(),
                                homeFilter = type
                            ))
                        }
                    } else {
                        for (i in start..end) {
                            list.add(PostReview(
                                id = "00${i + 1}",
                                imageProduct = mockImagePostReview(),
                                titleProduct = mockListTitle(),
                                userInfo = UserInfo(
                                    id = "10000${i}",
                                    imageUser = mocKImageUser(),
                                    nameUser = mockNameUser()
                                ),
                                numberHeart = (0..1000).random(),
                                homeFilter = type
                            ))
                        }
                    }
                }

                HOME_FILTER.FOLLOW -> {
                    val start = 800 * page
                    val end = start + 20

                    if (page == 12) {
                        for (i in start..end - 10) {
                            list.add(PostReview(
                                id = "00${i + 1}",
                                imageProduct = mockImagePostReview(),
                                titleProduct = mockListTitle(),
                                userInfo = UserInfo(
                                    id = "10000${i}",
                                    imageUser = mocKImageUser(),
                                    nameUser = mockNameUser()
                                ),
                                numberHeart = (0..1000).random(),
                                homeFilter = type
                            ))
                        }
                    } else {
                        for (i in start..end) {
                            list.add(PostReview(
                                id = "00${i + 1}",
                                imageProduct = mockImagePostReview(),
                                titleProduct = mockListTitle(),
                                userInfo = UserInfo(
                                    id = "10000${i}",
                                    imageUser = mocKImageUser(),
                                    nameUser = mockNameUser()
                                ),
                                numberHeart = (0..1000).random(),
                                homeFilter = type
                            ))
                        }
                    }
                }
            }
         return list
    }

    override fun getListReviewProducts(parent: Boolean): Flow<ReviewPost> {
        return flow {
            val listComment: MutableList<CommentPost> = arrayListOf()

            /**
             * add do bị tham chiếu đến các phần tử của list copy
             */
            reviewPost.listComment?.forEach {
                listComment.add(it.copy())
            }

            listComment.forEach {
                if (it.getListReplyComment().isNotEmpty()) {
                    it.childComment = arrayListOf()
                }
            }
            val data = ReviewPost(
                totalComment = this@PostReviewImpl.reviewPost.getTotalReview(),
                listComment = listComment
            )
            emit(data)
        }.flowOn(Dispatchers.IO)
    }

    override fun getListChildReview(parentId: String): Flow<List<CommentPost>> {
        return flow<List<CommentPost>> {
            val index = reviewPost.listComment?.indexOfFirst {
                it.commentId == parentId
            }
            val list: MutableList<CommentPost> = arrayListOf()
            if (index != null && index >= 0) {
                list.addAll(reviewPost.listComment?.get(index)?.childComment?.subList(0, 2)
                    ?: listOf())
            }
            emit(list)
        }.flowOn(Dispatchers.IO)
    }
}
