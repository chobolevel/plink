package com.plink.core.dto

import com.plink.core.vo.ResultType

data class ApiPagingResponse private constructor(
    val result: ResultType? = ResultType.SUCCESS,
    val data: List<Any>? = null,
    val page: Long,
    val size: Long
) {
    companion object {
        fun of(data: List<Any>, page: Long, size: Long): ApiPagingResponse {
            return ApiPagingResponse(
                result = ResultType.SUCCESS,
                data = data,
                page = page,
                size = size
            )
        }
    }
}
