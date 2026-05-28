package com.plink.api.post.updater

import com.plink.api.post.dto.UpdatePostRequest
import com.plink.core.post.entity.Post
import com.plink.core.post.vo.PostUpdateMask
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
