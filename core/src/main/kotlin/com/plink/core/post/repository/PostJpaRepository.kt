package com.plink.core.post.repository

import com.plink.core.post.entity.Post
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PostJpaRepository : JpaRepository<Post, String> {

    fun findByIdAndIsDeletedFalse(id: String): Post?
}
