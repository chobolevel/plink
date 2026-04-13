package com.plink.post.application

import com.plink.core.presentation.dto.ApiPagingResponse
import com.plink.core.presentation.dto.Paging
import com.plink.post.application.dto.CreatePostRequest
import com.plink.post.domain.model.Post
import com.plink.post.domain.model.PostOrderType
import com.plink.post.domain.repository.PostRepository
import com.plink.post.domain.service.PostConverter
import com.plink.post.infrastructure.persistence.PostQueryFilter
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
}
