package com.plink.post

import com.plink.post.domain.model.Post
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Post entity unit test")
class PostEntityTest {

    @Test
    fun `게시글 삭제 테스트`() {
        // given
        val dummyPost: Post = DummyPost.toEntity()

        // when
        dummyPost.delete()

        // then
        assertThat(dummyPost.isDeleted).isTrue
    }
}
