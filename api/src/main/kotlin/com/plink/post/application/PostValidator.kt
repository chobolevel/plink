package com.plink.post.application

import com.plink.core.domain.exception.ErrorCode
import com.plink.core.domain.exception.ForbiddenException
import com.plink.post.domain.model.Post
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
