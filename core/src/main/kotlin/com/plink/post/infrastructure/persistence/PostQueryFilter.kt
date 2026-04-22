package com.plink.post.infrastructure.persistence

import com.plink.post.domain.model.QPost.post
import com.querydsl.core.types.dsl.BooleanExpression

data class PostQueryFilter(
    private val userId: String?,
    private val title: String?
) {

    fun toBooleanExpressions(): Array<BooleanExpression> {
        return listOfNotNull(
            userId?.let { post.userId.eq(it) },
            title?.let { post.title.contains(it) },
            post.isDeleted.isFalse
        ).toTypedArray()
    }
}
