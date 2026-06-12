package com.plink.api.post.dto

import com.plink.core.post.vo.PostUpdateMask
import jakarta.validation.constraints.Size

data class UpdatePostRequest(
    val title: String?,
    val content: String?,
    @field:Size(min = 1)
    val updateMask: List<PostUpdateMask>
)
