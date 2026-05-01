package com.plink.api.post.application.converter

import com.plink.api.post.application.dto.CreatePostCommentRequest
import com.plink.core.post.domain.model.PostComment
import org.springframework.stereotype.Component

@Component
class PostCommentConverter {

    fun toEntity(request: CreatePostCommentRequest): PostComment {
        return PostComment(
            content = request.content
        )
    }
}
