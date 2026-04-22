package com.plink.post.domain.service

import com.plink.post.application.dto.UpdatePostRequest
import com.plink.post.domain.model.Post
import com.plink.post.domain.model.PostUpdateMask
import org.springframework.stereotype.Component

@Component
class PostUpdater {

    fun markAsUpdate(request: UpdatePostRequest, post: Post): Post {
        request.updateMask.forEach {
            when (it) {
                PostUpdateMask.TITLE -> post.title = request.title!!
                PostUpdateMask.CONTENT -> post.content = request.content!!
            }
        }
        return post
    }
}
