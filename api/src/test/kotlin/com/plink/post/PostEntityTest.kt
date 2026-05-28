package com.plink.post

import com.plink.core.post.entity.Post
import com.plink.core.user.entity.User
import com.plink.user.DummyUser
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Post entity unit test")
class PostEntityTest {

    private val dummyPost: Post = DummyPost.toEntity()

    @Test
    fun `게시글 작성자(회원) 할당 테스트`() {
        // given
        val dummyUser: User = DummyUser.toEntity()

        // when
        dummyPost.assignUser(user = dummyUser)

        // then
        assertThat(dummyPost.user).isEqualTo(dummyUser)
    }

    @Test
    fun `게시글 삭제 테스트`() {
        // given

        // when
        dummyPost.delete()

        // then
        assertThat(dummyPost.isDeleted).isTrue
    }
}
