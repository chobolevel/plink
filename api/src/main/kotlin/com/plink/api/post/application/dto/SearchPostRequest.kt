package com.plink.api.post.application.dto

import com.plink.core.post.domain.model.PostOrderType

data class SearchPostRequest(
    val userId: String?,
    val title: String?,
    val orderTypes: List<PostOrderType>?,
)
