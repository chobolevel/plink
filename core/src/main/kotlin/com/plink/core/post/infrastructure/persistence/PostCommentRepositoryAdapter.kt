package com.plink.core.post.infrastructure.persistence

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
}
