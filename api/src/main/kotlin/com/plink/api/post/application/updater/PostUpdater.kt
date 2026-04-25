package com.plink.api.post.application.updater

import com.plink.api.post.application.dto.UpdatePostRequest
import com.plink.core.post.domain.model.Post
import com.plink.core.post.domain.model.PostUpdateMask
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
