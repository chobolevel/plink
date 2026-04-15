package com.plink.user

import com.plink.user.domain.model.User
import com.plink.user.domain.model.UserPermission
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("UserPermission entity unit test")
class UserPermissionEntityTest {

    private val dummyUserPermission: UserPermission = DummyUserPermission.toEntity()

    @Test
    fun `회원 할당 테스트`() {
        // given
        val dummyUser: User = DummyUser.toEntity()

        // when
        dummyUserPermission.assignUser(user = dummyUser)

        // then
        assertThat(dummyUserPermission.user).isEqualTo(dummyUser)
    }
}
