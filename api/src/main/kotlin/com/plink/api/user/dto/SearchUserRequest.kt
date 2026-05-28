package com.plink.api.user.dto

import com.plink.core.user.vo.UserOrderType
import com.plink.core.user.vo.UserRoleType
import com.plink.core.user.vo.UserSignUpType

data class SearchUserRequest(
    val email: String?,
    val signUpType: UserSignUpType?,
    val nickname: String?,
    val role: UserRoleType?,
    val isResigned: Boolean?,
    val orderTypes: List<UserOrderType>?
)
