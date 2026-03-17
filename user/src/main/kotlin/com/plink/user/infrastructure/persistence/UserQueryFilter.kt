package com.plink.user.infrastructure.persistence

import com.plink.user.domain.model.QUser.user
import com.plink.user.domain.model.UserRoleType
import com.plink.user.domain.model.UserSignUpType
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
