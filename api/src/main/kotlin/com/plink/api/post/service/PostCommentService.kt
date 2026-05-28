package com.plink.api.post.service

import com.plink.api.post.assembler.PostCommentAssembler
import com.plink.api.post.converter.PostCommentConverter
import com.plink.api.post.dto.CreatePostCommentRequest
import com.plink.api.post.dto.PostCommentResponse
import com.plink.api.post.dto.UpdatePostCommentRequest
import com.plink.api.post.updater.PostCommentUpdater
import com.plink.api.post.validator.PostCommentValidator
import com.plink.core.common.dto.ApiPagingResponse
import com.plink.core.common.dto.Paging
import com.plink.core.post.entity.Post
import com.plink.core.post.entity.PostComment
import com.plink.core.post.repository.PostCommentQueryFilter
import com.plink.core.post.repository.PostCommentRepository
import com.plink.core.post.repository.PostRepository
import com.plink.core.post.vo.PostCommentOrderType
import com.plink.core.user.entity.User
import com.plink.core.user.repository.UserRepository
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
