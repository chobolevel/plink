package com.plink.api.post.dto

import com.plink.core.post.vo.PostCommentOrderType

data class SearchPostCommentRequest(
    val userId: String?,
    val parentId: String?,
    val orderTypes: List<PostCommentOrderType>?
)
