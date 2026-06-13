package com.plink.api.common.advice

import com.plink.core.common.dto.ErrorResponse
import com.plink.core.common.exception.BadCredentialException
import com.plink.core.common.exception.ErrorCode
import com.plink.core.common.exception.ForbiddenException
import com.plink.core.common.exception.InvalidParameterException
import com.plink.core.common.exception.UnAuthorizedException
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.security.access.AccessDeniedException as SecurityAccessDeniedException

@RestControllerAdvice
class GlobalExceptionHandler {

    private val log = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    @ExceptionHandler(SecurityAccessDeniedException::class)
    fun accessDeniedExceptionHandler(e: SecurityAccessDeniedException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
            ErrorResponse.of(
                code = ErrorCode.FORBIDDEN,
                message = ErrorCode.FORBIDDEN.koreanMessage
            )
        )
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun httpMessageNotReadableExceptionHandler(e: HttpMessageNotReadableException): ResponseEntity<ErrorResponse> {
        // 필드 특정하기가 어려움
        val message = e.rootCause?.message
        return ResponseEntity.badRequest().body(
            ErrorResponse.of(
                code = ErrorCode.INVALID_PARAMETER,
                message = message ?: ErrorCode.INVALID_PARAMETER.koreanMessage
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

    @ExceptionHandler(InvalidParameterException::class)
    fun invalidParameterExceptionHandler(e: InvalidParameterException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.badRequest().body(
            ErrorResponse.of(
                code = ErrorCode.INVALID_PARAMETER,
                message = e.message
            )
        )
    }

    @ExceptionHandler(UnAuthorizedException::class)
    fun unAuthorizedExceptionHandler(e: UnAuthorizedException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
            ErrorResponse.of(
                code = ErrorCode.INVALID_PARAMETER,
                message = e.message
            )
        )
    }

    @ExceptionHandler(ForbiddenException::class)
    fun forbiddenExceptionHandler(e: ForbiddenException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
            ErrorResponse.of(
                code = ErrorCode.FORBIDDEN,
                message = ErrorCode.FORBIDDEN.koreanMessage
            )
        )
    }

    @ExceptionHandler(BadCredentialException::class)
    fun badCredentialExceptionHandler(e: BadCredentialException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.badRequest().body(
            ErrorResponse.of(
                code = ErrorCode.BAD_CREDENTIAL,
                message = e.message
            )
        )
    }

    @ExceptionHandler(Exception::class)
    fun exceptionHandler(request: HttpServletRequest, e: Exception): ResponseEntity<ErrorResponse> {
        log.error("[(${request.method}) ${request.requestURL} ] Internal server error: ${e.message}", e)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
            ErrorResponse.of(
                code = ErrorCode.INTERNAL_SERVER_ERROR,
                message = ErrorCode.INTERNAL_SERVER_ERROR.koreanMessage
            )
        )
    }
}
