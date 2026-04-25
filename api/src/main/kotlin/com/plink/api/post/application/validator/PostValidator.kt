package com.plink.api.post.application.validator

import com.plink.core.common.domain.exception.ErrorCode
import com.plink.core.common.domain.exception.ForbiddenException
import com.plink.core.post.domain.model.Post
import org.springframework.stereotype.Component

@Component
class PostValidator {

    fun validateOwner(post: Post, userId: String) {
        if (post.userId != userId) {
            throw ForbiddenException(
                code = ErrorCode.FORBIDDEN,
                message = ErrorCode.FORBIDDEN.koreanMessage
            )
        }
    }
}
