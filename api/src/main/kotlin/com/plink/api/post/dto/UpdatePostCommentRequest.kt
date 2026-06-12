package com.plink.api.post.dto

import com.plink.core.post.vo.PostCommentUpdateMask
import jakarta.validation.constraints.Size

data class UpdatePostCommentRequest(
    val content: String?,
    @field:Size(min = 1)
    val updateMask: List<PostCommentUpdateMask>
)
