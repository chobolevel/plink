package com.plink.api.post.application.converter

import com.plink.api.post.application.dto.CreatePostCommentRequest
import com.plink.api.post.application.dto.PostCommentResponse
import com.plink.core.common.extension.toMillis
import com.plink.core.post.domain.model.PostComment
import org.springframework.stereotype.Component

@Component
class PostCommentConverter {

    fun toEntity(request: CreatePostCommentRequest): PostComment {
        return PostComment(
            content = request.content
        )
    }

    fun toResponse(postComment: PostComment): PostCommentResponse {
        return PostCommentResponse(
            id = postComment.id!!,
            userId = postComment.user!!.id!!,
            userNickname = postComment.user!!.nickname,
            content = postComment.content,
            parentId = postComment.parent?.id,
            createdAt = postComment.createdAt!!.toMillis(),
            updatedAt = postComment.updatedAt!!.toMillis()
        )
    }

    fun toResponseInBatch(postComments: List<PostComment>): List<PostCommentResponse> {
        return postComments.map { toResponse(it) }
    }
}
