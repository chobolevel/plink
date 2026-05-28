package com.plink.core.post.repository

import com.plink.core.post.entity.QPost.post
import com.querydsl.core.types.dsl.BooleanExpression

data class PostQueryFilter(
    private val userId: String?,
    private val title: String?
) {

    fun toBooleanExpressions(): Array<BooleanExpression> {
        return listOfNotNull(
            userId?.let { post.user.id.eq(it) },
            title?.let { post.title.contains(it) },
            post.isDeleted.isFalse
        ).toTypedArray()
    }
}
