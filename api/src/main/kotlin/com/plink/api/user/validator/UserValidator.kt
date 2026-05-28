package com.plink.api.user.validator

import com.plink.api.user.dto.CreateSocialUserRequest
import com.plink.api.user.dto.CreateUserRequest
import com.plink.core.common.exception.ErrorCode
import com.plink.core.common.exception.PolicyViolationException
import com.plink.core.user.repository.UserRepository
import com.plink.core.user.vo.UserSignUpType
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
