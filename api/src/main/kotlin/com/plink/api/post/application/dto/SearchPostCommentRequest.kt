package com.plink.api.post.application.dto

import com.plink.core.post.domain.model.PostCommentOrderType

data class SearchPostCommentRequest(
    val userId: String?,
    val parentId: String?,
    val orderTypes: List<PostCommentOrderType>?
)
