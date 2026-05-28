package com.plink.core.post.repository

import com.plink.core.common.dto.Paging
import com.plink.core.post.entity.PostComment
import com.plink.core.post.vo.PostCommentOrderType

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
