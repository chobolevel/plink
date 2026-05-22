package com.plink.api.post.application.updater

import com.plink.api.post.application.dto.UpdatePostCommentRequest
import com.plink.core.post.domain.model.PostComment
import com.plink.core.post.domain.model.PostCommentUpdateMask
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
