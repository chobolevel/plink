package com.plink.api.post.validator

import com.plink.api.post.dto.UpdatePostRequest
import com.plink.core.common.exception.ErrorCode
import com.plink.core.common.exception.ForbiddenException
import com.plink.core.common.exception.InvalidParameterException
import com.plink.core.common.util.HtmlUtil
import com.plink.core.post.entity.Post
import com.plink.core.post.vo.PostUpdateMask
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

    fun validate(request: UpdatePostRequest) {
        request.updateMask.forEach {
            when (it) {
                PostUpdateMask.TITLE -> {
                    if (request.title.isNullOrEmpty()) {
                        throw InvalidParameterException(
                            code = ErrorCode.INVALID_PARAMETER,
                            message = "게시글 제목은 필수 값입니다."
                        )
                    }
                    if (request.title.length < 10) {
                        throw InvalidParameterException(
                            code = ErrorCode.INVALID_PARAMETER,
                            message = "게시글 제목은 최소 10자 이상이어야 합니다."
                        )
                    }
                }
                PostUpdateMask.CONTENT -> {
                    if (request.content.isNullOrEmpty()) {
                        throw InvalidParameterException(
                            code = ErrorCode.INVALID_PARAMETER,
                            message = "게시글 내용은 필수 값입니다."
                        )
                    }
                    if (HtmlUtil.extractText(html = request.content).length < 20) {
                        throw InvalidParameterException(
                            code = ErrorCode.INVALID_PARAMETER,
                            message = "게시글 내용은 최소 20자 이상이어야 합니다."
                        )
                    }
                }
            }
        }
    }
}
