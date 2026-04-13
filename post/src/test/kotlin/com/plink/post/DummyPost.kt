package com.plink.post

import com.plink.post.application.dto.CreatePostRequest
import com.plink.post.application.dto.PostResponse
import com.plink.post.domain.model.Post

object DummyPost {
    private const val id: String = "dummyPostId"
    private const val userId: String = "dummyUserId"
    private const val userNickname: String = "dummyUserNickname"
    private const val title: String = "테스트 게시글 제목"
    private const val content: String = "<h1>테스트 게시글 내용</h1>"
    private const val createdAt: Long = 0L
    private const val updatedAt: Long = 0L

    private val dummyPost: Post by lazy {
        Post(
            userId = userId,
            userNickname = userNickname,
            title = title,
            content = content,
        ).also { it.id = id }
    }

    private val dummyPostResponse: PostResponse by lazy {
        PostResponse(
            id = id,
            userId = userId,
            userNickname = userNickname,
            title = title,
            content = content,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }

    private val dummyCreateRequest: CreatePostRequest by lazy {
        CreatePostRequest(
            userId = userId,
            userNickname = userNickname,
            title = title,
            content = content,
        )
    }

    fun toEntity(): Post = dummyPost

    fun toResponse(): PostResponse = dummyPostResponse

    fun toCreateRequest(): CreatePostRequest = dummyCreateRequest
}
