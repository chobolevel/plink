package com.plink.api.post.dto

import com.plink.core.post.vo.PostOrderType

data class SearchPostRequest(
    val userId: String?,
    val title: String?,
    val isDeleted: Boolean?,
    val orderTypes: List<PostOrderType>?,
)
