package com.plink.core.common.dto

import com.plink.core.common.entity.ResultType

data class ApiPagingResponse private constructor(
    val result: ResultType? = ResultType.SUCCESS,
    val page: Long,
    val size: Long,
    val totalCount: Long,
    val data: List<Any>? = null,
) {
    companion object {
        fun of(page: Long, size: Long, totalCount: Long, data: List<Any>): ApiPagingResponse {
            return ApiPagingResponse(
                result = ResultType.SUCCESS,
                page = page,
                size = size,
                totalCount = totalCount,
                data = data,
            )
        }
    }
}
