package com.plink.core.post.repository

import com.plink.core.common.dto.Paging
import com.plink.core.post.entity.Post
import com.plink.core.post.entity.QPost.post
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
