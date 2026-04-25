package com.plink.core.common.presentation.dto

import com.plink.core.common.domain.exception.ErrorCode
import com.plink.core.common.domain.model.ResultType

data class Error(
    val code: ErrorCode,
    val message: String
)

data class ErrorResponse private constructor(
    val result: ResultType? = ResultType.ERROR,
    val error: Error
) {

    companion object {
        fun of(code: ErrorCode, message: String): ErrorResponse {
            return ErrorResponse(
                result = ResultType.ERROR,
                error = Error(code, message)
            )
        }
    }
}
