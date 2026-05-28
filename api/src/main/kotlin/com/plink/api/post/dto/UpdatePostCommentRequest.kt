package com.plink.api.post.dto

import com.plink.core.common.exception.ErrorCode
import com.plink.core.common.exception.InvalidParameterException
import com.plink.core.post.vo.PostCommentUpdateMask
import jakarta.validation.constraints.Size

data class UpdatePostCommentRequest(
    val content: String?,
    @field:Size(min = 1)
    val updateMask: List<PostCommentUpdateMask>
) {
    init {
        updateMask.forEach {
            when (it) {
                PostCommentUpdateMask.CONTENT -> {
                    if (content.isNullOrEmpty()) {
                        throw InvalidParameterException(
                            code = ErrorCode.INVALID_PARAMETER,
                            message = "댓글 내용은 필수 값입니다."
                        )
                    }
                    if (content.length > 255) {
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
