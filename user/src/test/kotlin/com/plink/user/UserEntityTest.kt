package com.plink.user

import com.plink.user.domain.model.User
import com.plink.user.domain.model.UserPermission
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

    @Test
    fun `회원 권한 추가 테스트`() {
        // given
        val dummyUserPermission: UserPermission = DummyUserPermission.toEntity()
        val dummyUserPermissions: List<UserPermission> = listOf(dummyUserPermission)

        // when
        dummyUser.addUserPermissions(userPermissions = dummyUserPermissions)

        // then
        assertThat(dummyUser.userPermissions.first().resource).isEqualTo(dummyUserPermission.resource)
        assertThat(dummyUser.userPermissions.first().action).isEqualTo(dummyUserPermission.action)
    }
}
