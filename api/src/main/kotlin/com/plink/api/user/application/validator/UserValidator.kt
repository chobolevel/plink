package com.plink.api.user.application.validator

import com.plink.api.user.application.dto.CreateSocialUserRequest
import com.plink.api.user.application.dto.CreateUserRequest
import com.plink.core.common.domain.exception.ErrorCode
import com.plink.core.common.domain.exception.PolicyViolationException
import com.plink.core.user.domain.model.UserSignUpType
import com.plink.core.user.domain.repository.UserRepository
import org.springframework.stereotype.Component

@Component
class UserValidator(
    private val userRepository: UserRepository
) {

    fun validate(request: CreateUserRequest) {
        if (userRepository.existsByEmailAndSignUpType(email = request.email, signUpType = UserSignUpType.COMMON)) {
            throw PolicyViolationException(
                code = ErrorCode.ALREADY_EXISTS_EMAIL,
                message = ErrorCode.ALREADY_EXISTS_EMAIL.koreanMessage
            )
        }
    }

    fun validate(request: CreateSocialUserRequest) {
        if (userRepository.existsByEmailAndSignUpType(email = request.email, signUpType = request.signUpType)) {
            throw PolicyViolationException(
                code = ErrorCode.ALREADY_EXISTS_EMAIL,
                message = ErrorCode.ALREADY_EXISTS_EMAIL.koreanMessage
            )
        }
    }
}
