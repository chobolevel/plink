package com.plink.api.user.validator

import com.plink.api.user.dto.CreateSocialUserRequest
import com.plink.api.user.dto.CreateUserRequest
import com.plink.api.user.dto.UpdateUserRequest
import com.plink.core.common.constant.RegexConstant
import com.plink.core.common.exception.ErrorCode
import com.plink.core.common.exception.InvalidParameterException
import com.plink.core.common.exception.PolicyViolationException
import com.plink.core.user.repository.UserRepository
import com.plink.core.user.vo.UserSignUpType
import com.plink.core.user.vo.UserUpdateMask
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
                    if (!Regex(RegexConstant.NICKNAME_REGEX).matches(request.nickname)) {
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
