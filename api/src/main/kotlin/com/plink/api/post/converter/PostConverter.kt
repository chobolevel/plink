package com.plink.api.post.converter

import com.plink.api.post.dto.CreatePostRequest
import com.plink.api.post.dto.PostResponse
import com.plink.core.common.extension.toMillis
import com.plink.core.post.entity.Post
import com.plink.core.user.entity.User
import org.springframework.stereotype.Component

@Component
class PostConverter {

    fun toEntity(request: CreatePostRequest): Post {
        return Post(
            title = request.title,
            content = request.content,
        )
    }

    fun toResponse(post: Post): PostResponse {
        val user: User = post.user!!
        return PostResponse(
            id = post.id!!,
            userId = user.id!!,
            userNickname = user.nickname,
            title = post.title,
            content = post.content,
            createdAt = post.createdAt!!.toMillis(),
            updatedAt = post.updatedAt!!.toMillis()
        )
    }

    fun toResponseInBatch(posts: List<Post>): List<PostResponse> = posts.map { toResponse(it) }
}
