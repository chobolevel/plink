package com.plink.api.post.validator

import com.plink.core.common.exception.ErrorCode
import com.plink.core.common.exception.ForbiddenException
import com.plink.core.post.entity.Post
import org.springframework.stereotype.Component

@Component
class PostValidator {

    fun validateOwner(post: Post, userId: String) {
        if (post.user?.id != userId) {
            throw ForbiddenException(
                code = ErrorCode.FORBIDDEN,
                message = ErrorCode.FORBIDDEN.koreanMessage
            )
        }
    }
}
