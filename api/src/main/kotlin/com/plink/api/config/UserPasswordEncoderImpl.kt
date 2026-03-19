package com.plink.api.config

import com.plink.core.exception.BadCredentialException
import com.plink.core.exception.ErrorCode
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

    override fun match(rawPassword: String, encodedPassword: String) {
        if (!bcryptPasswordEncoder.matches(rawPassword, encodedPassword)) {
            throw BadCredentialException(
                code = ErrorCode.BAD_CREDENTIAL,
                message = ErrorCode.BAD_CREDENTIAL.koreanMessage
            )
        }
    }
}
