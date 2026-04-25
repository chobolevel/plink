package com.plink.core.post.infrastructure.persistence

import com.plink.core.common.presentation.dto.Paging
import com.plink.core.post.domain.model.Post
import com.plink.core.post.domain.model.QPost.post
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.dsl.BooleanExpression
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class PostQueryDslRepository : QuerydslRepositorySupport(Post::class.java) {

    fun searchPosts(
        booleanExpressions: Array<BooleanExpression>,
        paging: Paging,
        orderSpecifiers: Array<OrderSpecifier<*>>
    ): List<Post> {
        return from(post)
            .where(*booleanExpressions)
            .orderBy(*orderSpecifiers)
            .offset(paging.offset)
            .limit(paging.limit)
            .fetch()
    }

    fun searchPostsCount(booleanExpressions: Array<BooleanExpression>): Long {
        return from(post)
            .where(*booleanExpressions)
            .fetchCount()
    }
}
