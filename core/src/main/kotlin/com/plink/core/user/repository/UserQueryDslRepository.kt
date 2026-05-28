package com.plink.core.user.repository

import com.plink.core.common.dto.Paging
import com.plink.core.user.entity.QUser.user
import com.plink.core.user.entity.User
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.dsl.BooleanExpression
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class UserQueryDslRepository : QuerydslRepositorySupport(User::class.java) {

    fun searchUsers(
        booleanExpressions: Array<BooleanExpression>,
        paging: Paging,
        orderSpecifiers: Array<OrderSpecifier<*>>,
    ): List<User> {
        return from(user)
            .where(*booleanExpressions)
            .orderBy(*orderSpecifiers)
            .offset(paging.offset)
            .limit(paging.limit)
            .fetch()
    }

    fun searchUsersCount(booleanExpressions: Array<BooleanExpression>): Long {
        return from(user)
            .where(*booleanExpressions)
            .fetchCount()
    }
}
