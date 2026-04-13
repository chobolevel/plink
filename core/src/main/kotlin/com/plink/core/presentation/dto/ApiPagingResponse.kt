package com.plink.core.presentation.dto

import com.plink.core.domain.model.ResultType

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
