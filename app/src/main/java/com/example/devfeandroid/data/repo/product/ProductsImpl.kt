package com.example.devfeandroid.data.repo.product

import com.example.devfeandroid.data.model.producthome.HOME_FILTER
import com.example.devfeandroid.data.model.producthome.Products
import com.example.devfeandroid.data.model.review.CommentProduct
import com.example.devfeandroid.data.model.review.ReviewProduct
import com.example.devfeandroid.data.model.userinfo.UserInfo
import com.example.devfeandroid.data.repo.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class ProductsImpl : IProductsRepo {

    private val totalReview = 323

    private val reviewProduct = ReviewProduct(
        totalComment = totalReview,
        listComment = mockListCommentProduct().subList(0, 20)
    )

    override fun getProductList(type: HOME_FILTER, page: Int): Flow<List<Products>> {
        val list: MutableList<Products> = arrayListOf()
        when (type) {
            HOME_FILTER.ALL -> {
                val start = 20 * page
                val end = start + 20
                if (page == 4) {
                    for (i in start..end - 10) {
                        list.add(Products(
                            id = "00${i + 1}",
                            imageProduct = mockImageProduct(),
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
                        list.add(Products(
                            id = "00${i + 1}",
                            imageProduct = mockImageProduct(),
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
                        list.add(Products(
                            id = "00${i + 1}",
                            imageProduct = mockImageProduct(),
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
                        list.add(Products(
                            id = "00${i + 1}",
                            imageProduct = mockImageProduct(),
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
                        list.add(Products(
                            id = "00${i + 1}",
                            imageProduct = mockImageProduct(),
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
                        list.add(Products(
                            id = "00${i + 1}",
                            imageProduct = mockImageProduct(),
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
                        list.add(Products(
                            id = "00${i + 1}",
                            imageProduct = mockImageProduct(),
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
                        list.add(Products(
                            id = "00${i + 1}",
                            imageProduct = mockImageProduct(),
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
        return flow {
            emit(list)
        }.flowOn(Dispatchers.IO)
    }

    override fun getListReviewProducts(parent: Boolean): Flow<ReviewProduct> {
        return flow {
            val listComment: MutableList<CommentProduct> = arrayListOf()

            /**
             * add do bị tham chiếu đến các phần tử của list copy
             */
            reviewProduct.listComment?.forEach {
                listComment.add(it.copy())
            }

            listComment.forEach {
                if (it.getListReplyComment().isNotEmpty()) {
                    it.childComment = arrayListOf()
                }
            }
            val data = ReviewProduct(
                totalComment = this@ProductsImpl.reviewProduct.getTotalReview(),
                listComment = listComment
            )
            emit(data)
        }.flowOn(Dispatchers.IO)
    }

    override fun getListChildReview(parentId: String): Flow<List<CommentProduct>> {
        return flow<List<CommentProduct>> {
            val index = reviewProduct.listComment?.indexOfFirst {
                it.commentId == parentId
            }
            val list: MutableList<CommentProduct> = arrayListOf()
            if (index != null && index >= 0) {
                list.addAll(reviewProduct.listComment?.get(index)?.childComment?.subList(0, 2)
                    ?: listOf())
            }
            emit(list)
        }.flowOn(Dispatchers.IO)
    }
}
