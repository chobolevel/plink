package com.plink.user.domain.service

import com.plink.core.domain.exception.ErrorCode
import com.plink.core.domain.exception.PolicyViolationException
import com.plink.user.application.dto.CreateSocialUserRequest
import com.plink.user.application.dto.CreateUserRequest
import com.plink.user.domain.model.UserSignUpType
import com.plink.user.domain.repository.UserRepository
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
