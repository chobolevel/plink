package com.plink.api.post.application

import com.plink.api.post.application.assembler.PostCommentAssembler
import com.plink.api.post.application.converter.PostCommentConverter
import com.plink.api.post.application.dto.CreatePostCommentRequest
import com.plink.core.post.domain.model.Post
import com.plink.core.post.domain.model.PostComment
import com.plink.core.post.domain.repository.PostCommentRepository
import com.plink.core.post.domain.repository.PostRepository
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
    private val postCommentAssembler: PostCommentAssembler
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
        val assembledPostComment: PostComment = postCommentAssembler.assemble(
            postComment = postComment,
            post = post,
            user = user
        )
        return postCommentRepository.save(postComment = assembledPostComment).id!!
    }
}
