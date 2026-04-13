package com.plink.post.application.dto

data class PostResponse(
    val id: String,
    val userId: String,
    val userNickname: String,
    val title: String,
    val content: String,
    val createdAt: Long,
    val updatedAt: Long,
)
