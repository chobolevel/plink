package com.plink.user.domain.service

import com.plink.core.domain.exception.ErrorCode
import com.plink.core.domain.exception.InvalidParameterException
import com.plink.core.domain.exception.PolicyViolationException
import com.plink.user.application.dto.CreateSocialUserRequest
import com.plink.user.application.dto.CreateUserRequest
import com.plink.user.application.dto.UpdateUserRequest
import com.plink.user.domain.model.UserSignUpType
import com.plink.user.domain.model.UserUpdateMask
import com.plink.user.domain.repository.UserRepository
import org.springframework.stereotype.Component

@Component
class UserValidator(
    private val userRepository: UserRepository
) {

    private val nicknameRegexp: Regex = Regex("^[가-힣a-zA-Z0-9]{2,20}$")

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

    fun validate(request: UpdateUserRequest) {
        request.updateMask.forEach {
            when (it) {
                UserUpdateMask.NICKNAME -> {
                    if (request.nickname.isNullOrEmpty()) {
                        throw InvalidParameterException(
                            code = ErrorCode.INVALID_PARAMETER,
                            message = "닉네임은 필수 값입니다."
                        )
                    }
                    if (!nicknameRegexp.matches(request.nickname)) {
                        throw InvalidParameterException(
                            code = ErrorCode.INVALID_PARAMETER,
                            message = "닉네임 형식이 올바르지 않습니다."
                        )
                    }
                }
            }
        }
    }
}
