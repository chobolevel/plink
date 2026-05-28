package com.plink.post

import com.plink.core.post.entity.Post
import com.plink.core.post.entity.PostComment
import com.plink.core.user.entity.User
import com.plink.user.DummyUser
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("PostComment entity unit test")
class PostCommentEntityTest {

    private val dummyPostComment: PostComment = DummyPostComment.toEntity()

    @Test
    fun `게시글 엔티티 할당 테스트`() {
        // given
        val dummyPost: Post = DummyPost.toEntity()

        // when
        dummyPostComment.assignPost(post = dummyPost)

        // then
        assertThat(dummyPostComment.post).isEqualTo(dummyPost)
    }

    @Test
    fun `작성자(회원) 할당 테스트`() {
        // given
        val dummyUser: User = DummyUser.toEntity()

        // when
        dummyPostComment.assignUser(user = dummyUser)

        // then
        assertThat(dummyPostComment.user).isEqualTo(dummyUser)
    }

    @Test
    fun `부모 댓글 할당 테스트`() {
        // given
        val dummyParentPostComment: PostComment = DummyPostComment.toParentEntity()

        // when
        dummyPostComment.assignParent(postComment = dummyParentPostComment)

        // then
        assertThat(dummyPostComment.parent).isEqualTo(dummyParentPostComment)
    }
}
