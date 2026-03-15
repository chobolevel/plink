package com.plink.user.infrastructure.persistence

import com.plink.user.domain.model.User
import com.plink.user.domain.repository.UserRepository
import org.springframework.stereotype.Component

@Component
class UserRepositoryAdapter(
    private val userJpaRepository: UserJpaRepository,
    private val userQueryDslRepository: UserQueryDslRepository
) : UserRepository {

    override fun save(user: User): User {
        return userJpaRepository.save(user)
    }
}
