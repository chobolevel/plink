package com.plink.core.post.domain.repository

import com.plink.core.common.presentation.dto.Paging
import com.plink.core.post.domain.model.PostComment
import com.plink.core.post.domain.model.PostCommentOrderType
import com.plink.core.post.infrastructure.persistence.PostCommentQueryFilter

interface PostCommentRepository {

    fun save(postComment: PostComment): PostComment

    fun findById(id: String): PostComment

    fun searchPostComments(
        queryFilter: PostCommentQueryFilter,
        paging: Paging,
        orderTypes: List<PostCommentOrderType>
    ): List<PostComment>

    fun searchPostCommentsCount(queryFilter: PostCommentQueryFilter): Long
}
