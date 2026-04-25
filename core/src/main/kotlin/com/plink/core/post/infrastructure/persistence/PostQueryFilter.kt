package com.plink.core.post.infrastructure.persistence

import com.plink.core.post.domain.model.QPost.post
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
