package com.plink.core.post.infrastructure.persistence

import com.plink.core.post.domain.model.QPostComment.postComment
import com.querydsl.core.types.dsl.BooleanExpression

data class PostCommentQueryFilter(
    private val postId: String?,
    private val userId: String?,
    private val parentId: String?
) {

    fun toBooleanExpressions(): Array<BooleanExpression> {
        return listOfNotNull(
            postId?.let { postComment.post.id.eq(it) },
            userId?.let { postComment.user.id.eq(it) },
            parentId?.let { postComment.parent.id.eq(it) },
            postComment.isDeleted.isFalse
        ).toTypedArray()
    }
}
