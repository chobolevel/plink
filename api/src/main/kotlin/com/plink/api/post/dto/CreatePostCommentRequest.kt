package com.plink.api.post.dto

import jakarta.validation.constraints.NotEmpty

data class CreatePostCommentRequest(
    val parentId: String?,
    @field:NotEmpty(message = "게시글 댓글 내용은 필수 값입니다.")
    val content: String,
)
