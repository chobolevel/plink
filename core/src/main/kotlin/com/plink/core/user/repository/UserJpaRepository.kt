package com.plink.core.user.repository

import com.plink.core.user.entity.User
import com.plink.core.user.vo.UserSignUpType
import org.springframework.data.jpa.repository.JpaRepository

interface UserJpaRepository : JpaRepository<User, String> {

    fun existsByEmail(email: String): Boolean

    fun existsByEmailAndSignUpTypeAndIsResignedFalse(email: String, signUpType: UserSignUpType): Boolean

    fun findByIdAndIsResignedFalse(id: String): User?

    fun findByEmailAndSignUpTypeAndIsResignedFalse(email: String, signUpType: UserSignUpType): User?

    fun findByEmailAndSocialIdAndSignUpTypeAndIsResignedFalse(email: String, socialId: String, signUpType: UserSignUpType): User?
}
