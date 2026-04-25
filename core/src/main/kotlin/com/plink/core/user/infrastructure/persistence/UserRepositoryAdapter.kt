package com.plink.core.user.infrastructure.persistence

import com.plink.core.common.domain.exception.DataNotFoundException
import com.plink.core.common.domain.exception.ErrorCode
import com.plink.core.common.presentation.dto.Paging
import com.plink.core.user.domain.model.QUser.user
import com.plink.core.user.domain.model.User
import com.plink.core.user.domain.model.UserOrderType
import com.plink.core.user.domain.model.UserSignUpType
import com.plink.core.user.domain.repository.UserRepository
import com.querydsl.core.types.OrderSpecifier
import org.springframework.stereotype.Component

@Component
class UserRepositoryAdapter(
    private val userJpaRepository: UserJpaRepository,
    private val userQueryDslRepository: UserQueryDslRepository
) : UserRepository {

    override fun save(user: User): User {
        return userJpaRepository.save(user)
    }

    override fun existsByEmail(email: String): Boolean {
        return userJpaRepository.existsByEmail(email = email)
    }

    override fun existsByEmailAndSignUpType(email: String, signUpType: UserSignUpType): Boolean {
        return userJpaRepository.existsByEmailAndSignUpTypeAndIsResignedFalse(email, signUpType)
    }

    override fun findById(id: String): User {
        return userJpaRepository.findByIdAndIsResignedFalse(id) ?: throw DataNotFoundException(
            code = ErrorCode.USER_NOT_FOUND,
            message = ErrorCode.USER_NOT_FOUND.koreanMessage
        )
    }

    override fun findByEmailAndSignUpType(email: String, signUpType: UserSignUpType): User? {
        return userJpaRepository.findByEmailAndSignUpTypeAndIsResignedFalse(email, signUpType)
    }

    override fun findByEmailAndSocialIdAndSignUpType(
        email: String,
        socialId: String,
        signUpType: UserSignUpType
    ): User? {
        return userJpaRepository.findByEmailAndSocialIdAndSignUpTypeAndIsResignedFalse(email, socialId, signUpType)
    }

    override fun searchUsers(
        queryFilter: UserQueryFilter,
        paging: Paging,
        orderTypes: List<UserOrderType>
    ): List<User> {
        return userQueryDslRepository.searchUsers(
            booleanExpressions = queryFilter.toBooleanExpressions(),
            paging = paging,
            orderSpecifiers = orderTypes.toOrderSpecifiers()
        )
    }

    override fun searchUsersCount(queryFilter: UserQueryFilter): Long {
        return userQueryDslRepository.searchUsersCount(booleanExpressions = queryFilter.toBooleanExpressions())
    }

    private fun List<UserOrderType>.toOrderSpecifiers(): Array<OrderSpecifier<*>> {
        return this.map {
            when (it) {
                UserOrderType.CREATED_AT_ASC -> user.createdAt.asc()
                UserOrderType.CREATED_AT_DESC -> user.createdAt.desc()
            }
        }.toTypedArray()
    }
}
