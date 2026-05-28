package com.plink.api.post.validator

import com.plink.core.common.exception.ErrorCode
import com.plink.core.common.exception.ForbiddenException
import com.plink.core.post.entity.PostComment
import org.springframework.stereotype.Component

@Component
class PostCommentValidator {

    fun validateOwner(postComment: PostComment, userId: String) {
        if (postComment.user?.id != userId) {
            throw ForbiddenException(
                code = ErrorCode.FORBIDDEN,
                message = ErrorCode.FORBIDDEN.koreanMessage
            )
        }
    }
}
