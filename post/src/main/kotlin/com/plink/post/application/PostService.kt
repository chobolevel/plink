package com.plink.post.application

import com.plink.post.application.dto.CreatePostRequest
import com.plink.post.domain.model.Post
import com.plink.post.domain.repository.PostRepository
import com.plink.post.domain.service.PostConverter
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostService(
    private val postRepository: PostRepository,
    private val postConverter: PostConverter
) {

    @Transactional
    fun createPost(request: CreatePostRequest): String {
        val post: Post = postConverter.toEntity(request = request)
        return postRepository.save(post = post).id!!
    }
}
