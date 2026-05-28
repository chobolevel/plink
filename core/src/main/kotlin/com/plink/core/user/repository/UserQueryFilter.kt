package com.plink.core.user.repository

import com.plink.core.user.entity.QUser.user
import com.plink.core.user.vo.UserRoleType
import com.plink.core.user.vo.UserSignUpType
import com.querydsl.core.types.dsl.BooleanExpression

data class UserQueryFilter(
    private val email: String?,
    private val signUpType: UserSignUpType?,
    private val nickname: String?,
    private val role: UserRoleType?,
    private val isResigned: Boolean?,
) {

    fun toBooleanExpressions(): Array<BooleanExpression> {
        return listOfNotNull(
            email?.let { user.email.contains(it) },
            signUpType?.let { user.signUpType.eq(it) },
            nickname?.let { user.nickname.contains(it) },
            role?.let { user.role.eq(it) },
            isResigned?.let { user.isResigned.eq(it) }
        ).toTypedArray()
    }
}
