package com.plink.api.post.application.dto

data class PostCommentResponse(
    val id: String,
    val userId: String,
    val userNickname: String,
    val content: String,
    val parentId: String?,
    val createdAt: Long,
    val updatedAt: Long,
)
