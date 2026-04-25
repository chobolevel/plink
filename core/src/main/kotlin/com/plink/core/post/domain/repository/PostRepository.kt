package com.plink.core.post.domain.repository

import com.plink.core.common.presentation.dto.Paging
import com.plink.core.post.domain.model.Post
import com.plink.core.post.domain.model.PostOrderType
import com.plink.core.post.infrastructure.persistence.PostQueryFilter

interface PostRepository {

    fun save(post: Post): Post

    fun findById(id: String): Post

    fun searchPosts(queryFilter: PostQueryFilter, paging: Paging, orderTypes: List<PostOrderType>): List<Post>

    fun searchPostsCount(queryFilter: PostQueryFilter): Long
}
