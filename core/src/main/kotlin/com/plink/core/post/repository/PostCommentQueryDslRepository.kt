package com.plink.core.post.repository

import com.plink.core.common.dto.Paging
import com.plink.core.post.entity.PostComment
import com.plink.core.post.entity.QPostComment.postComment
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.dsl.BooleanExpression
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class PostCommentQueryDslRepository : QuerydslRepositorySupport(PostComment::class.java) {

    fun searchPostComments(
        booleanExpressions: Array<BooleanExpression>,
        paging: Paging,
        orderSpecifiers: Array<OrderSpecifier<*>>
    ): List<PostComment> {
        return from(postComment)
            .where(*booleanExpressions)
            .orderBy(*orderSpecifiers)
            .offset(paging.offset)
            .limit(paging.limit)
            .fetch()
    }

    fun searchPostCommentsCount(booleanExpressions: Array<BooleanExpression>): Long {
        return from(postComment)
            .where(*booleanExpressions)
            .fetchCount()
    }
}
