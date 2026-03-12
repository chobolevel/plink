package com.plink.core.exception

open class PlinkException(
    open val code: String,
    override val message: String,
    open val throwable: Throwable? = null
) : RuntimeException(message)

open class InvalidParameterException(
    override val code: String,
    override val message: String,
    override val throwable: Throwable? = null
) : PlinkException(code, message, throwable)

open class PolicyViolationException(
    override val code: String,
    override val message: String,
    override val throwable: Throwable? = null
) : PlinkException(code, message, throwable)

open class UnAuthorizedException(
    override val code: String,
    override val message: String,
    override val throwable: Throwable? = null
) : PlinkException(code, message, throwable)

open class ForbiddenException(
    override val code: String,
    override val message: String,
    override val throwable: Throwable? = null
) : PlinkException(code, message, throwable)

open class DataNotFoundException(
    override val code: String,
    override val message: String,
    override val throwable: Throwable? = null
) : PlinkException(code, message, throwable)
