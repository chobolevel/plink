package com.plink.core.post.domain.repository

import com.plink.core.post.domain.model.PostComment

interface PostCommentRepository {

    fun save(postComment: PostComment): PostComment
}
