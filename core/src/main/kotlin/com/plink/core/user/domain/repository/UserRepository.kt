package com.plink.core.user.domain.repository

import com.plink.core.common.presentation.dto.Paging
import com.plink.core.user.domain.model.User
import com.plink.core.user.domain.model.UserOrderType
import com.plink.core.user.domain.model.UserSignUpType
import com.plink.core.user.infrastructure.persistence.UserQueryFilter

interface UserRepository {

    fun save(user: User): User

    fun existsByEmail(email: String): Boolean

    fun existsByEmailAndSignUpType(email: String, signUpType: UserSignUpType): Boolean

    fun findById(id: String): User

    fun findByEmailAndSignUpType(email: String, signUpType: UserSignUpType): User?

    fun findByEmailAndSocialIdAndSignUpType(email: String, socialId: String, signUpType: UserSignUpType): User?

    fun searchUsers(
        queryFilter: UserQueryFilter,
        paging: Paging,
        orderTypes: List<UserOrderType>
    ): List<User>

    fun searchUsersCount(queryFilter: UserQueryFilter): Long
}
