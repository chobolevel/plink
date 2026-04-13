package com.plink.post.domain.repository

import com.plink.core.presentation.dto.Paging
import com.plink.post.domain.model.Post
import com.plink.post.domain.model.PostOrderType
import com.plink.post.infrastructure.persistence.PostQueryFilter

interface PostRepository {

    fun save(post: Post): Post

    fun searchPosts(queryFilter: PostQueryFilter, paging: Paging, orderTypes: List<PostOrderType>): List<Post>

    fun searchPostsCount(queryFilter: PostQueryFilter): Long
}
