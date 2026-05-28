package com.plink.core.user.repository

import com.plink.core.common.dto.Paging
import com.plink.core.user.entity.User
import com.plink.core.user.vo.UserOrderType
import com.plink.core.user.vo.UserSignUpType

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
