package com.plink.api.post.service

import com.plink.api.post.assembler.PostAssembler
import com.plink.api.post.converter.PostConverter
import com.plink.api.post.dto.CreatePostRequest
import com.plink.api.post.dto.PostResponse
import com.plink.api.post.dto.UpdatePostRequest
import com.plink.api.post.updater.PostUpdater
import com.plink.api.post.validator.PostValidator
import com.plink.core.common.dto.ApiPagingResponse
import com.plink.core.common.dto.Paging
import com.plink.core.post.entity.Post
import com.plink.core.post.repository.PostQueryFilter
import com.plink.core.post.repository.PostRepository
import com.plink.core.post.vo.PostOrderType
import com.plink.core.user.entity.User
import com.plink.core.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostService(
    private val postRepository: PostRepository,
    private val userRepository: UserRepository,
    private val postConverter: PostConverter,
    private val postAssembler: PostAssembler,
    private val postUpdater: PostUpdater,
    private val postValidator: PostValidator
) {

    @Transactional
    fun createPost(userId: String, request: CreatePostRequest): String {
        val post: Post = postConverter.toEntity(
            request = request
        )
        val user: User = userRepository.findById(id = userId)
        val assembledPost: Post = postAssembler.assemble(
            post = post,
            user = user
        )
        return postRepository.save(post = assembledPost).id!!
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
