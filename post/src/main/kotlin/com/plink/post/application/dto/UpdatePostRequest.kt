package com.plink.post.application.dto

import com.plink.core.domain.exception.ErrorCode
import com.plink.core.domain.exception.InvalidParameterException
import com.plink.core.infrastructure.util.HtmlUtil
import com.plink.post.domain.model.PostUpdateMask
import jakarta.validation.constraints.Size

data class UpdatePostRequest(
    val title: String?,
    val content: String?,
    @field:Size(min = 1)
    val updateMask: List<PostUpdateMask>
) {
    init {
        // init 블록에서 던진 예외는 Jackson이 객체를 생성하는 도중 발생
        // 따라서 Jackson이 이를 잡아서 HttpMessageNotReadableException으로 항상 감싸버림
        updateMask.forEach {
            when (it) {
                PostUpdateMask.TITLE -> {
                    if (title.isNullOrEmpty()) {
                        throw InvalidParameterException(
                            code = ErrorCode.INVALID_PARAMETER,
                            message = "게시글 제목은 필수 값입니다."
                        )
                    }
                    if (title.length < 10) {
                        throw InvalidParameterException(
                            code = ErrorCode.INVALID_PARAMETER,
                            message = "게시글 제목은 최소 10자 이상이어야 합니다."
                        )
                    }
                }
                PostUpdateMask.CONTENT -> {
                    if (content.isNullOrEmpty()) {
                        throw InvalidParameterException(
                            code = ErrorCode.INVALID_PARAMETER,
                            message = "게시글 내용은 필수 값입니다."
                        )
                    }
                    if (HtmlUtil.extractText(html = content).length < 20) {
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
