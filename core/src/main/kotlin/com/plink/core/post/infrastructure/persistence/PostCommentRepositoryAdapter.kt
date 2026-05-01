package com.plink.core.post.infrastructure.persistence

import com.plink.core.common.domain.exception.DataNotFoundException
import com.plink.core.common.domain.exception.ErrorCode
import com.plink.core.post.domain.model.PostComment
import com.plink.core.post.domain.repository.PostCommentRepository
import org.springframework.stereotype.Component

@Component
class PostCommentRepositoryAdapter(
    private val postCommentJpaRepository: PostCommentJpaRepository
) : PostCommentRepository {

    override fun save(postComment: PostComment): PostComment {
        return postCommentJpaRepository.save(postComment)
    }

    override fun findById(id: String): PostComment {
        return postCommentJpaRepository.findByIdAndIsDeletedFalse(id) ?: throw DataNotFoundException(
            code = ErrorCode.POST_COMMENT_NOT_FOUND,
            message = ErrorCode.POST_COMMENT_NOT_FOUND.koreanMessage
        )
    }
}
