package com.plink.api.user.application.dto

import com.plink.core.user.domain.model.UserOrderType
import com.plink.core.user.domain.model.UserRoleType
import com.plink.core.user.domain.model.UserSignUpType

data class SearchUserRequest(
    val email: String?,
    val signUpType: UserSignUpType?,
    val nickname: String?,
    val role: UserRoleType?,
    val isResigned: Boolean?,
    val orderTypes: List<UserOrderType>?
)
