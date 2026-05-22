package com.plink.core.post.infrastructure.persistence

import com.plink.core.common.presentation.dto.Paging
import com.plink.core.post.domain.model.PostComment
import com.plink.core.post.domain.model.QPostComment.postComment
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
