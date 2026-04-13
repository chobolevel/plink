package com.plink.post.infrastructure.persistence

import com.plink.post.domain.model.Post
import com.plink.post.domain.repository.PostRepository
import org.springframework.stereotype.Component

@Component
class PostRepositoryAdapter(
    private val postJpaRepository: PostJpaRepository
) : PostRepository {

    override fun save(post: Post): Post {
        return postJpaRepository.save(post)
    }
}
