package com.plink.core.post.infrastructure.persistence

import com.plink.core.post.domain.model.PostComment
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class PostCommentQueryDslRepository : QuerydslRepositorySupport(PostComment::class.java)
