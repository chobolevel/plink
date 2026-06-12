package com.plink.api.post.validator

import com.plink.api.post.dto.UpdatePostCommentRequest
import com.plink.core.common.exception.ErrorCode
import com.plink.core.common.exception.ForbiddenException
import com.plink.core.common.exception.InvalidParameterException
import com.plink.core.post.entity.PostComment
import com.plink.core.post.vo.PostCommentUpdateMask
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

    fun validate(request: UpdatePostCommentRequest) {
        request.updateMask.forEach {
            when (it) {
                PostCommentUpdateMask.CONTENT -> {
                    if (request.content.isNullOrEmpty()) {
                        throw InvalidParameterException(
                            code = ErrorCode.INVALID_PARAMETER,
                            message = "댓글 내용은 필수 값입니다."
                        )
                    }
                    if (request.content.length > 255) {
                        throw InvalidParameterException(
                            code = ErrorCode.INVALID_PARAMETER,
                            message = "댓글 내용은 최대 255자까지 입력 가능합니다."
                        )
                    }
                }
            }
        }
    }
}
