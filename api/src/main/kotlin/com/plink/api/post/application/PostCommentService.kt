package com.plink.api.post.application

import com.plink.api.post.application.assembler.PostCommentAssembler
import com.plink.api.post.application.converter.PostCommentConverter
import com.plink.api.post.application.dto.CreatePostCommentRequest
import com.plink.api.post.application.dto.PostCommentResponse
import com.plink.api.post.application.dto.UpdatePostCommentRequest
import com.plink.api.post.application.updater.PostCommentUpdater
import com.plink.api.post.application.validator.PostCommentValidator
import com.plink.core.common.presentation.dto.ApiPagingResponse
import com.plink.core.common.presentation.dto.Paging
import com.plink.core.post.domain.model.Post
import com.plink.core.post.domain.model.PostComment
import com.plink.core.post.domain.model.PostCommentOrderType
import com.plink.core.post.domain.repository.PostCommentRepository
import com.plink.core.post.domain.repository.PostRepository
import com.plink.core.post.infrastructure.persistence.PostCommentQueryFilter
import com.plink.core.user.domain.model.User
import com.plink.core.user.domain.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostCommentService(
    private val postCommentRepository: PostCommentRepository,
    private val postRepository: PostRepository,
    private val userRepository: UserRepository,
    private val postCommentConverter: PostCommentConverter,
    private val postCommentAssembler: PostCommentAssembler,
    private val postCommentValidator: PostCommentValidator,
    private val postCommentUpdater: PostCommentUpdater
) {

    @Transactional
    fun createPostComment(
        userId: String,
        postId: String,
        request: CreatePostCommentRequest
    ): String {
        val user: User = userRepository.findById(id = userId)
        val post: Post = postRepository.findById(id = postId)
        val postComment: PostComment = postCommentConverter.toEntity(request = request)
        val parentPostComment: PostComment? = request.parentId?.let { postCommentRepository.findById(id = it) }
        val assembledPostComment: PostComment = postCommentAssembler.assemble(
            postComment = postComment,
            parentPostComment = parentPostComment,
            post = post,
            user = user
        )
        return postCommentRepository.save(postComment = assembledPostComment).id!!
    }

    @Transactional(readOnly = true)
    fun getPostComments(
        queryFilter: PostCommentQueryFilter,
        paging: Paging,
        orderTypes: List<PostCommentOrderType>
    ): ApiPagingResponse {
        val postComments: List<PostComment> = postCommentRepository.searchPostComments(
            queryFilter = queryFilter,
            paging = paging,
            orderTypes = orderTypes
        )
        val totalCount: Long = postCommentRepository.searchPostCommentsCount(queryFilter = queryFilter)
        return ApiPagingResponse.of(
            page = paging.page,
            size = paging.size,
            data = postCommentConverter.toResponseInBatch(postComments = postComments),
            totalCount = totalCount
        )
    }

    @Transactional(readOnly = true)
    fun getPostComment(postCommentId: String): PostCommentResponse {
        val postComment: PostComment = postCommentRepository.findById(id = postCommentId)
        return postCommentConverter.toResponse(postComment = postComment)
    }

    @Transactional
    fun updatePostComment(
        userId: String,
        postCommentId: String,
        request: UpdatePostCommentRequest
    ): String {
        val postComment: PostComment = postCommentRepository.findById(id = postCommentId)
        postCommentValidator.validateOwner(
            postComment = postComment,
            userId = userId
        )
        val updatedPostComment: PostComment = postCommentUpdater.markAsUpdate(
            request = request,
            postComment = postComment
        )
        return updatedPostComment.id!!
    }

    @Transactional
    fun deletePostComment(
        userId: String,
        postCommentId: String
    ): Boolean {
        val postComment: PostComment = postCommentRepository.findById(id = postCommentId)
        postCommentValidator.validateOwner(
            postComment = postComment,
            userId = userId
        )
        postComment.delete()
        return true
    }
}
