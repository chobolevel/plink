package com.plink.post.application.dto

import com.plink.core.domain.exception.ErrorCode
import com.plink.core.domain.exception.InvalidParameterException
import com.plink.post.domain.model.PostUpdateMask
import jakarta.validation.constraints.Size

data class UpdatePostRequest(
    val title: String?,
    val content: String?,
    @field:Size(min = 1)
    val updateMask: List<PostUpdateMask>
) {
    init {
        if (PostUpdateMask.TITLE in updateMask) {
            require(!title.isNullOrEmpty()) {
                throw InvalidParameterException(
                    code = ErrorCode.INVALID_PARAMETER,
                    message = "게시글 제목은 필수 값입니다."
                )
            }
        }
        if (PostUpdateMask.CONTENT in updateMask) {
            require(!content.isNullOrEmpty()) {
                throw InvalidParameterException(
                    code = ErrorCode.INVALID_PARAMETER,
                    message = "게시글 내용은 필수 값입니다."
                )
            }
        }
    }
}
