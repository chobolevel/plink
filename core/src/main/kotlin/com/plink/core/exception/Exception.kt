package com.plink.core.exception

open class PlinkException(
    open val code: ErrorCode,
    override val message: String,
    open val throwable: Throwable? = null
) : RuntimeException(message)

open class InvalidParameterException(
    override val code: ErrorCode,
    override val message: String,
    override val throwable: Throwable? = null
) : PlinkException(code, message, throwable)

open class PolicyViolationException(
    override val code: ErrorCode,
    override val message: String,
    override val throwable: Throwable? = null
) : PlinkException(code, message, throwable)

open class BadCredentialException(
    override val code: ErrorCode,
    override val message: String,
    override val throwable: Throwable? = null
) : PlinkException(code, message, throwable)

open class UnAuthorizedException(
    override val code: ErrorCode,
    override val message: String,
    override val throwable: Throwable? = null
) : PlinkException(code, message, throwable)

open class ForbiddenException(
    override val code: ErrorCode,
    override val message: String,
    override val throwable: Throwable? = null
) : PlinkException(code, message, throwable)

open class DataNotFoundException(
    override val code: ErrorCode,
    override val message: String,
    override val throwable: Throwable? = null
) : PlinkException(code, message, throwable)
