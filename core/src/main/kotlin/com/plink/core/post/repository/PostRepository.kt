package com.plink.core.post.repository

import com.plink.core.common.dto.Paging
import com.plink.core.post.entity.Post
import com.plink.core.post.vo.PostOrderType

interface PostRepository {

    fun save(post: Post): Post

    fun findById(id: String): Post

    fun searchPosts(queryFilter: PostQueryFilter, paging: Paging, orderTypes: List<PostOrderType>): List<Post>

    fun searchPostsCount(queryFilter: PostQueryFilter): Long
}
