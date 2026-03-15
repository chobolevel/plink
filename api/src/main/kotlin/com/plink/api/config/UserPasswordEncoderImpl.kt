package com.plink.api.config

import com.plink.user.domain.service.UserPasswordEncoder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component

@Component
class UserPasswordEncoderImpl(
    private val bcryptPasswordEncoder: BCryptPasswordEncoder,
) : UserPasswordEncoder {

    override fun encode(rawPassword: String): String {
        return bcryptPasswordEncoder.encode(rawPassword)
    }
}
