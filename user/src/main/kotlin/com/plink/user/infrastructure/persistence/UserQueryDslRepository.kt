package com.plink.user.infrastructure.persistence

import com.plink.core.dto.Paging
import com.plink.user.domain.model.QUser.user
import com.plink.user.domain.model.User
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
            .offset(paging.page)
            .limit(paging.limit)
            .fetch()
    }
}
