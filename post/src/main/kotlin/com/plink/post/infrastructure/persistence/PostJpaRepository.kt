package com.plink.post.infrastructure.persistence

import com.plink.post.domain.model.Post
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PostJpaRepository : JpaRepository<Post, String>
