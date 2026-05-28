package com.plink.core.post.repository

import com.plink.core.post.entity.PostComment
import org.springframework.data.jpa.repository.JpaRepository

interface PostCommentJpaRepository : JpaRepository<PostComment, String> {

    fun findByIdAndIsDeletedFalse(id: String): PostComment?

    fun findAllByPostIdAndIsDeletedFalse(postId: String): List<PostComment>
}
