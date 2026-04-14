package com.plink.user.application.dto

import com.plink.core.domain.constant.RegexConstant
import com.plink.core.domain.exception.ErrorCode
import com.plink.core.domain.exception.InvalidParameterException
import com.plink.user.domain.model.UserUpdateMask
import jakarta.validation.constraints.Size

data class UpdateUserRequest(
    val nickname: String?,
    @field:Size(min = 1)
    val updateMask: List<UserUpdateMask>
) {
    init {
        updateMask.forEach {
            when (it) {
                UserUpdateMask.NICKNAME -> {
                    if (nickname.isNullOrEmpty()) {
                        throw InvalidParameterException(
                            code = ErrorCode.INVALID_PARAMETER,
                            message = "닉네임은 필수 값입니다."
                        )
                    }
                    if (!Regex(RegexConstant.NICKNAME_REGEX).matches(nickname)) {
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
