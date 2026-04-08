package com.plink.core.presentation.dto

import com.plink.core.domain.model.ResultType

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
