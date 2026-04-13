package com.plink.post.domain.repository

import com.plink.post.domain.model.Post

interface PostRepository {

    fun save(post: Post): Post
}
