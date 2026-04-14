package com.plink.post

import com.plink.post.application.dto.CreatePostRequest
import com.plink.post.application.dto.PostResponse
import com.plink.post.application.dto.UpdatePostRequest
import com.plink.post.domain.model.Post
import com.plink.post.domain.model.PostUpdateMask

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
            userNickname = userNickname,
            title = title,
            content = content,
        )
    }

    private val dummyUpdateRequest: UpdatePostRequest by lazy {
        UpdatePostRequest(
            title = "변경하고자 하는 새로운 제목입니다.",
            content = "<h1>변경하고자 하는 새로운 게시글의 내용입니다. 감사합니다.</h1>",
            updateMask = listOf(PostUpdateMask.TITLE, PostUpdateMask.CONTENT)
        )
    }

    fun toEntity(): Post = dummyPost

    fun toResponse(): PostResponse = dummyPostResponse

    fun toCreateRequest(): CreatePostRequest = dummyCreateRequest

    fun toUpdateRequest(): UpdatePostRequest = dummyUpdateRequest
}
