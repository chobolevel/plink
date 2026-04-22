package com.plink.post.application

import com.plink.core.presentation.dto.ApiPagingResponse
import com.plink.core.presentation.dto.Paging
import com.plink.post.application.dto.CreatePostRequest
import com.plink.post.application.dto.PostResponse
import com.plink.post.application.dto.UpdatePostRequest
import com.plink.post.domain.model.Post
import com.plink.post.domain.model.PostOrderType
import com.plink.post.domain.repository.PostRepository
import com.plink.post.domain.service.PostConverter
import com.plink.post.domain.service.PostUpdater
import com.plink.post.domain.service.PostValidator
import com.plink.post.infrastructure.persistence.PostQueryFilter
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostService(
    private val postRepository: PostRepository,
    private val postConverter: PostConverter,
    private val postUpdater: PostUpdater,
    private val postValidator: PostValidator
) {

    @Transactional
    fun createPost(userId: String, request: CreatePostRequest): String {
        val post: Post = postConverter.toEntity(
            userId = userId,
            request = request
        )
        return postRepository.save(post = post).id!!
    }

    @Transactional(readOnly = true)
    fun getPost(postId: String): PostResponse {
        val post: Post = postRepository.findById(id = postId)
        return postConverter.toResponse(post = post)
    }

    @Transactional(readOnly = true)
    fun getPosts(
        queryFilter: PostQueryFilter,
        paging: Paging,
        orderTypes: List<PostOrderType>
    ): ApiPagingResponse {
        val posts: List<Post> = postRepository.searchPosts(
            queryFilter = queryFilter,
            paging = paging,
            orderTypes = orderTypes
        )
        val totalCount: Long = postRepository.searchPostsCount(
            queryFilter = queryFilter,
        )
        return ApiPagingResponse.of(
            page = paging.page,
            size = paging.size,
            data = postConverter.toResponseInBatch(posts = posts),
            totalCount = totalCount
        )
    }

    @Transactional
    fun updatePost(postId: String, userId: String, request: UpdatePostRequest): String {
        val post: Post = postRepository.findById(id = postId)
        postValidator.validateOwner(
            post = post,
            userId = userId
        )
        val updatedPost: Post = postUpdater.markAsUpdate(request = request, post = post)
        return updatedPost.id!!
    }

    @Transactional
    fun deletePost(postId: String, userId: String): Boolean {
        val post: Post = postRepository.findById(id = postId)
        postValidator.validateOwner(
            post = post,
            userId = userId
        )
        post.delete()
        return true
    }
}
