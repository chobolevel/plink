package com.plink.core.common.presentation.dto

import com.plink.core.common.domain.model.ResultType

data class ApiResponse private constructor(
    val result: ResultType,
    val data: Any? = null
) {
    companion object {
        fun of(data: Any?): ApiResponse {
            return ApiResponse(
                result = ResultType.SUCCESS,
                data = data
            )
        }
    }
}
