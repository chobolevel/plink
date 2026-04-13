package com.plink.post.domain.service

import com.plink.post.application.dto.CreatePostRequest
import com.plink.post.domain.model.Post
import org.springframework.stereotype.Component

@Component
class PostConverter {

    fun toEntity(request: CreatePostRequest): Post {
        return Post(
            userId = request.userId,
            userNickname = request.userNickname,
            title = request.title,
            content = request.content,
        )
    }
}
