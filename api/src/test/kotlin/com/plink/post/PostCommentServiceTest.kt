package com.plink.post

import com.plink.api.post.application.PostCommentService
import com.plink.api.post.application.assembler.PostCommentAssembler
import com.plink.api.post.application.converter.PostCommentConverter
import com.plink.api.post.application.dto.CreatePostCommentRequest
import com.plink.core.common.domain.exception.DataNotFoundException
import com.plink.core.common.domain.exception.ErrorCode
import com.plink.core.post.domain.model.Post
import com.plink.core.post.domain.model.PostComment
import com.plink.core.post.domain.repository.PostCommentRepository
import com.plink.core.post.domain.repository.PostRepository
import com.plink.core.user.domain.model.User
import com.plink.core.user.domain.repository.UserRepository
import com.plink.user.DummyUser
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension

@DisplayName("PostCommentService unit test")
@ExtendWith(MockitoExtension::class)
class PostCommentServiceTest {

    private val dummyPostComment: PostComment = DummyPostComment.toEntity()

    private val dummyParentPostComment: PostComment = DummyPostComment.toParentEntity()

    private val dummyPost: Post = DummyPost.toEntity()

    private val dummyUser: User = DummyUser.toEntity()

    @Mock
    private lateinit var postCommentRepository: PostCommentRepository

    @Mock
    private lateinit var postRepository: PostRepository

    @Mock
    private lateinit var userRepository: UserRepository

    @Mock
    private lateinit var postCommentConverter: PostCommentConverter

    @Mock
    private lateinit var postCommentAssembler: PostCommentAssembler

    @InjectMocks
    private lateinit var postCommentService: PostCommentService

    @Test
    fun `게시글 댓글 등록 테스트`() {
        // given
        val dummyUserId: String = dummyUser.id!!
        val dummyPostId: String = dummyPost.id!!
        val request: CreatePostCommentRequest = DummyPostComment.toCreateRequest()
        `when`(postCommentConverter.toEntity(request = request)).thenReturn(dummyPostComment)
        `when`(postRepository.findById(id = dummyPostId)).thenReturn(dummyPost)
        `when`(userRepository.findById(id = dummyUserId)).thenReturn(dummyUser)
        `when`(postCommentRepository.findById(id = request.parentId!!)).thenReturn(dummyParentPostComment)
        `when`(
            postCommentAssembler.assemble(
                postComment = dummyPostComment,
                parentPostComment = dummyParentPostComment,
                post = dummyPost,
                user = dummyUser
            )
        ).thenReturn(dummyPostComment)
        `when`(postCommentRepository.save(postComment = dummyPostComment)).thenReturn(dummyPostComment)

        // when
        val result: String = postCommentService.createPostComment(
            userId = dummyUserId,
            postId = dummyPostId,
            request = request
        )

        // then
        assertThat(result).isEqualTo(dummyPostComment.id)
    }

    @Test
    fun `존재하지 않는 게시글에 게시글 댓글 등록 시 예외 발생`() {
        // given
        val dummyUserId: String = dummyUser.id!!
        val dummyPostId: String = dummyPost.id!!
        val request: CreatePostCommentRequest = DummyPostComment.toCreateRequest()
        `when`(userRepository.findById(id = dummyUserId)).thenReturn(dummyUser)
        `when`(postRepository.findById(id = dummyPostId)).thenThrow(
            DataNotFoundException(
                code = ErrorCode.POST_NOT_FOUND,
                message = ErrorCode.POST_NOT_FOUND.koreanMessage
            )
        )

        // when & then
        assertThatThrownBy {
            postCommentService.createPostComment(
                userId = dummyUserId,
                postId = dummyPostId,
                request = request
            )
        }
            .isInstanceOf(DataNotFoundException::class.java)
            .hasMessage(ErrorCode.POST_NOT_FOUND.koreanMessage)
    }

    @Test
    fun `존재하지 않는 회원이 게시글 댓글 등록 시 예외 발생`() {
        // given
        val dummyUserId: String = dummyUser.id!!
        val dummyPostId: String = dummyPost.id!!
        val request: CreatePostCommentRequest = DummyPostComment.toCreateRequest()
        `when`(userRepository.findById(id = dummyUserId)).thenThrow(
            DataNotFoundException(
                code = ErrorCode.USER_NOT_FOUND,
                message = ErrorCode.USER_NOT_FOUND.koreanMessage
            )
        )

        // when & then
        assertThatThrownBy {
            postCommentService.createPostComment(
                userId = dummyUserId,
                postId = dummyPostId,
                request = request
            )
        }
            .isInstanceOf(DataNotFoundException::class.java)
            .hasMessage(ErrorCode.USER_NOT_FOUND.koreanMessage)
    }

    @Test
    fun `존재하지 않는 부모 게시글 댓글에 게시글 댓글 등록 시 예외 발생`() {
        // given
        val dummyUserId: String = dummyUser.id!!
        val dummyPostId: String = dummyPost.id!!
        val request: CreatePostCommentRequest = DummyPostComment.toCreateRequest()
        `when`(userRepository.findById(id = dummyUserId)).thenReturn(dummyUser)
        `when`(postRepository.findById(id = dummyPostId)).thenReturn(dummyPost)
        `when`(postCommentRepository.findById(id = request.parentId!!)).thenThrow(
            DataNotFoundException(
                code = ErrorCode.POST_COMMENT_NOT_FOUND,
                message = ErrorCode.POST_COMMENT_NOT_FOUND.koreanMessage
            )
        )

        // when & then
        assertThatThrownBy {
            postCommentService.createPostComment(
                userId = dummyUserId,
                postId = dummyPostId,
                request = request
            )
        }
            .isInstanceOf(DataNotFoundException::class.java)
            .hasMessage(ErrorCode.POST_COMMENT_NOT_FOUND.koreanMessage)
    }
}
