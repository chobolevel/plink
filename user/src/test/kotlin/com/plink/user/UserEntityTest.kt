package com.plink.user

import com.plink.user.domain.model.User
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("User entity unit test")
class UserEntityTest {

    private val dummyUser: User = DummyUser.toEntity()

    @Test
    fun resignTest() {
        // given & when
        dummyUser.resign()

        // then
        assertThat(dummyUser.isResigned).isTrue
    }
}
