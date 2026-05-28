package com.plink.api.user.dto

import com.plink.core.common.constant.RegexConstant
import com.plink.core.common.exception.ErrorCode
import com.plink.core.common.exception.InvalidParameterException
import com.plink.core.user.vo.UserUpdateMask
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
