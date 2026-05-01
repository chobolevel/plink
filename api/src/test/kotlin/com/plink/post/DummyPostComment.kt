package com.plink.post

import com.plink.api.post.application.dto.CreatePostCommentRequest
import com.plink.core.post.domain.model.PostComment

object DummyPostComment {
    private val id: String = "dummyPostCommentId"
    private val content: String = "게시글 댓글"
    private val createdAt: Long = 0L
    private val updatedAt: Long = 0L

    private val parentId: String = "parentDummyPostCommentId"
    private val parentContent: String = "부모 개시글 댓글"
    private val parentCreatedAt: Long = 0L
    private val parentUpdatedAt: Long = 0L

    private val dummyPostComment: PostComment by lazy {
        PostComment(
            content = content
        ).also { it.id = id }
    }

    private val dummyParentPostComment: PostComment by lazy {
        PostComment(
            content = parentContent
        ).also { it.id = parentId }
    }

    private val dummyCreatePostCommentRequest: CreatePostCommentRequest by lazy {
        CreatePostCommentRequest(
            parentId = parentId,
            content = content
        )
    }

    fun toEntity(): PostComment = dummyPostComment

    fun toParentEntity(): PostComment = dummyParentPostComment

    fun toCreateRequest(): CreatePostCommentRequest = dummyCreatePostCommentRequest
}
