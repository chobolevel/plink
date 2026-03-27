package com.plink.api.controller.v1

import com.plink.core.dto.ErrorResponse
import com.plink.core.exception.ErrorCode
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class CustomControllerAdvice {

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun httpMessageNotReadableExceptionHandler(e: HttpMessageNotReadableException): ResponseEntity<ErrorResponse> {
        // 필드 특정하기가 어려움
        return ResponseEntity.badRequest().body(
            ErrorResponse.of(
                code = ErrorCode.INVALID_PARAMETER,
                message = ErrorCode.INVALID_PARAMETER.koreanMessage
            )
        )
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun methodArgumentNotValidExceptionHandler(e: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.badRequest().body(
            ErrorResponse.of(
                code = ErrorCode.INVALID_PARAMETER,
                message = e.fieldError?.defaultMessage ?: ErrorCode.INVALID_PARAMETER.koreanMessage
            )
        )
    }
}
