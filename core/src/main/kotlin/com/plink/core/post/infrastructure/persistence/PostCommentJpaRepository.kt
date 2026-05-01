package com.plink.core.post.infrastructure.persistence

import com.plink.core.post.domain.model.PostComment
import org.springframework.data.jpa.repository.JpaRepository

interface PostCommentJpaRepository : JpaRepository<PostComment, String>
