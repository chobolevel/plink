package com.plink.api.post.updater

import com.plink.api.post.dto.UpdatePostCommentRequest
import com.plink.core.post.entity.PostComment
import com.plink.core.post.vo.PostCommentUpdateMask
import org.springframework.stereotype.Component

@Component
class PostCommentUpdater {

    fun markAsUpdate(request: UpdatePostCommentRequest, postComment: PostComment): PostComment {
        request.updateMask.forEach {
            when (it) {
                PostCommentUpdateMask.CONTENT -> postComment.content = request.content!!
            }
        }
        return postComment
    }
}
