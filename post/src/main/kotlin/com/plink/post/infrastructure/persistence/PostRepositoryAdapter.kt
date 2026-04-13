package com.plink.post.infrastructure.persistence

import com.plink.core.presentation.dto.Paging
import com.plink.post.domain.model.Post
import com.plink.post.domain.model.PostOrderType
import com.plink.post.domain.model.QPost.post
import com.plink.post.domain.repository.PostRepository
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
                PostOrderType.CREATED_AT_DESC -> post.updatedAt.desc()
            }
        }.toTypedArray()
    }
}
