package com.plink.user.infrastructure.persistence

import com.plink.core.dto.Paging
import com.plink.user.domain.model.QUser.user
import com.plink.user.domain.model.User
import com.plink.user.domain.model.UserOrderType
import com.plink.user.domain.repository.UserRepository
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
