package com.plink.post

import com.plink.api.post.application.dto.CreatePostRequest
import com.plink.api.post.application.dto.PostResponse
import com.plink.api.post.application.dto.UpdatePostRequest
import com.plink.api.user.application.dto.UserResponse
import com.plink.core.post.domain.model.Post
import com.plink.core.post.domain.model.PostUpdateMask
import com.plink.user.DummyUser

object DummyPost {
    private const val id: String = "dummyPostId"
    private const val title: String = "테스트 게시글 제목"
    private const val content: String = "<h1>테스트 게시글 내용</h1>"
    private const val createdAt: Long = 0L
    private const val updatedAt: Long = 0L

    private val dummyPost: Post by lazy {
        Post(
            title = title,
            content = content,
        ).also { it.id = id }
    }

    private val dummyPostResponse: PostResponse by lazy {
        val dummyUserResponse: UserResponse = DummyUser.toResponse()
        PostResponse(
            id = id,
            userId = dummyUserResponse.id,
            userNickname = dummyUserResponse.nickname,
            title = title,
            content = content,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }

    private val dummyCreateRequest: CreatePostRequest by lazy {
        CreatePostRequest(
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
