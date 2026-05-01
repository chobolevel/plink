package com.plink.user

import com.plink.core.user.domain.model.User
import com.plink.core.user.domain.model.UserPermission
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("User entity unit test")
class UserEntityTest {

    private val dummyUser: User = DummyUser.toEntity()

    private val dummyUserPermission: UserPermission = DummyUserPermission.toEntity()

    @Test
    fun resignTest() {
        // given & when
        dummyUser.resign()

        // then
        assertThat(dummyUser.isResigned).isTrue
    }

    @Test
    fun `회원 권한 추가`() {
        // given
        val dummyUserPermission: UserPermission = DummyUserPermission.toEntity()

        // when
        dummyUser.addUserPermission(userPermission = dummyUserPermission)

        // then
        assertThat(dummyUser.userPermissions).contains(dummyUserPermission)
    }

    @Test
    fun `회원 권한 일괄 추가 테스트`() {
        // given
        val dummyUserPermissions: List<UserPermission> = listOf(dummyUserPermission)

        // when
        dummyUser.addUserPermissions(userPermissions = dummyUserPermissions)

        // then
        assertThat(dummyUser.userPermissions.first().resource).isEqualTo(dummyUserPermission.resource)
        assertThat(dummyUser.userPermissions.first().action).isEqualTo(dummyUserPermission.action)
    }

    @Test
    fun `회원 권한 제거`() {
        // given
        val dummyUserPermissionId: String = dummyUserPermission.id!!
        dummyUser.addUserPermission(userPermission = dummyUserPermission)

        // when
        dummyUser.subUserPermissionById(id = dummyUserPermissionId)

        // then
        assertThat(dummyUser.userPermissions).doesNotContain(dummyUserPermission)
    }
}
