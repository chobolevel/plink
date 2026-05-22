package com.plink.api.post.application.validator

import com.plink.core.common.domain.exception.ErrorCode
import com.plink.core.common.domain.exception.ForbiddenException
import com.plink.core.post.domain.model.PostComment
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
