package com.plink.post.application

import com.plink.core.extension.toMillis
import com.plink.post.application.dto.CreatePostRequest
import com.plink.post.application.dto.PostResponse
import com.plink.post.domain.model.Post
import org.springframework.stereotype.Component

@Component
class PostConverter {

    fun toEntity(userId: String, request: CreatePostRequest): Post {
        return Post(
            userId = userId,
            userNickname = request.userNickname,
            title = request.title,
            content = request.content,
        )
    }

    fun toResponse(post: Post): PostResponse {
        return PostResponse(
            id = post.id!!,
            userId = post.userId,
            userNickname = post.userNickname,
            title = post.title,
            content = post.content,
            createdAt = post.createdAt!!.toMillis(),
            updatedAt = post.updatedAt!!.toMillis()
        )
    }

    fun toResponseInBatch(posts: List<Post>): List<PostResponse> = posts.map { toResponse(it) }
}
