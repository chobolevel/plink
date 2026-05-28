package com.plink.core.post.repository

import com.plink.core.common.dto.Paging
import com.plink.core.common.exception.DataNotFoundException
import com.plink.core.common.exception.ErrorCode
import com.plink.core.post.entity.Post
import com.plink.core.post.entity.QPost.post
import com.plink.core.post.vo.PostOrderType
import com.querydsl.core.types.OrderSpecifier
import org.springframework.stereotype.Component

@Component
class PostRepositoryAdapter(
    private val postJpaRepository: PostJpaRepository,
    private val postQueryDslRepository: PostQueryDslRepository
) : PostRepository {

    override fun save(post: Post): Post {
        return postJpaRepository.save(post)
    }

    override fun findById(id: String): Post {
        return postJpaRepository.findByIdAndIsDeletedFalse(id = id) ?: throw DataNotFoundException(
            code = ErrorCode.POST_NOT_FOUND,
            message = ErrorCode.POST_NOT_FOUND.koreanMessage
        )
    }

    override fun searchPosts(
        queryFilter: PostQueryFilter,
        paging: Paging,
        orderTypes: List<PostOrderType>
    ): List<Post> {
        return postQueryDslRepository.searchPosts(
            booleanExpressions = queryFilter.toBooleanExpressions(),
            paging = paging,
            orderSpecifiers = orderTypes.toOrderSpecifiers()
        )
    }

    override fun searchPostsCount(queryFilter: PostQueryFilter): Long {
        return postQueryDslRepository.searchPostsCount(
            booleanExpressions = queryFilter.toBooleanExpressions(),
        )
    }

    private fun List<PostOrderType>.toOrderSpecifiers(): Array<OrderSpecifier<*>> {
        return this.map {
            when (it) {
                PostOrderType.CREATED_AT_ASC -> post.createdAt.asc()
                PostOrderType.CREATED_AT_DESC -> post.createdAt.desc()
            }
        }.toTypedArray()
    }
}
