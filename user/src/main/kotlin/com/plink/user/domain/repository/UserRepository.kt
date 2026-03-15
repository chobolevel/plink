package com.plink.user.domain.repository

import com.plink.user.domain.model.User

interface UserRepository {

    fun save(user: User): User
}
