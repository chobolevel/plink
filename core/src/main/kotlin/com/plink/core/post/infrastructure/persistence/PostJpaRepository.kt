package com.plink.core.post.infrastructure.persistence

import com.plink.core.post.domain.model.Post
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PostJpaRepository : JpaRepository<Post, String> {

    fun findByIdAndIsDeletedFalse(id: String): Post?
}
