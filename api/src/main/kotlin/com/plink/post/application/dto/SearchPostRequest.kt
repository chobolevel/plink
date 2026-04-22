package com.plink.post.application.dto

import com.plink.post.domain.model.PostOrderType

data class SearchPostRequest(
    val userId: String?,
    val title: String?,
    val orderTypes: List<PostOrderType>?,
)
