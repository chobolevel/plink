package com.plink.core.common.dto

import com.plink.core.common.entity.ResultType

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
