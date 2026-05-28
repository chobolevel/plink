package com.plink.post

import com.plink.api.post.assembler.PostCommentAssembler
import com.plink.api.post.converter.PostCommentConverter
import com.plink.api.post.dto.CreatePostCommentRequest
import com.plink.api.post.dto.PostCommentResponse
import com.plink.api.post.dto.UpdatePostCommentRequest
import com.plink.api.post.service.PostCommentService
import com.plink.api.post.updater.PostCommentUpdater
import com.plink.api.post.validator.PostCommentValidator
import com.plink.core.common.dto.ApiPagingResponse
import com.plink.core.common.dto.Paging
import com.plink.core.common.exception.DataNotFoundException
import com.plink.core.common.exception.ErrorCode
import com.plink.core.common.exception.ForbiddenException
import com.plink.core.post.entity.Post
import com.plink.core.post.entity.PostComment
import com.plink.core.post.repository.PostCommentQueryFilter
import com.plink.core.post.repository.PostCommentRepository
import com.plink.core.post.repository.PostRepository
import com.plink.core.post.vo.PostCommentOrderType
import com.plink.core.user.entity.User
import com.plink.core.user.repository.UserRepository
import com.plink.user.DummyUser
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension

@DisplayName("PostCommentService unit test")
@ExtendWith(MockitoExtension::class)
class PostCommentServiceTest {

    private val dummyPostComment: PostComment = DummyPostComment.toEntity()

    private val dummyParentPostComment: PostComment = DummyPostComment.toParentEntity()

    private val dummyPostCommentResponse: PostCommentResponse = DummyPostComment.toResponse()

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

    @Mock
    private lateinit var postCommentValidator: PostCommentValidator

    @Mock
    private lateinit var postCommentUpdater: PostCommentUpdater

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

    @Test
    fun `게시글 댓글 목록 조회 테스트`() {
        // given
        val queryFilter = PostCommentQueryFilter(
            postId = null,
            userId = null,
            parentId = null,
        )
        val paging = Paging(
            page = 1,
            size = 10
        )
        val orderTypes: List<PostCommentOrderType> = emptyList()
        val dummyPostComments: List<PostComment> = listOf(dummyPostComment)
        val dummyPostCommentResponses: List<PostCommentResponse> = listOf(dummyPostCommentResponse)
        `when`(
            postCommentRepository.searchPostComments(
                queryFilter = queryFilter,
                paging = paging,
                orderTypes = orderTypes
            )
        ).thenReturn(dummyPostComments)
        `when`(
            postCommentRepository.searchPostCommentsCount(
                queryFilter = queryFilter,
            )
        ).thenReturn(dummyPostComments.size.toLong())
        `when`(postCommentConverter.toResponseInBatch(postComments = dummyPostComments)).thenReturn(dummyPostCommentResponses)

        // when
        val result: ApiPagingResponse = postCommentService.getPostComments(
            queryFilter = queryFilter,
            paging = paging,
            orderTypes = orderTypes
        )

        // then
        assertThat(result.page).isEqualTo(1)
        assertThat(result.size).isEqualTo(10)
        assertThat(result.data).isEqualTo(dummyPostCommentResponses)
        assertThat(result.totalCount).isEqualTo(dummyPostComments.size.toLong())
    }

    @Test
    fun `게시글 댓글 단건 조회 테스트`() {
        // given
        val dummyPostCommentId: String = dummyPostComment.id!!
        val response: PostCommentResponse = DummyPostComment.toResponse()
        `when`(postCommentRepository.findById(id = dummyPostCommentId)).thenReturn(dummyPostComment)
        `when`(postCommentConverter.toResponse(postComment = dummyPostComment)).thenReturn(response)

        // when
        val result: PostCommentResponse = postCommentService.getPostComment(postCommentId = dummyPostCommentId)

        // then
        assertThat(result).isEqualTo(response)
    }

    @Test
    fun `존재하지 않는 게시글 댓글 단건 조회 시 예외 발생`() {
        // given
        val dummyPostCommentId: String = "nonExistentCommentId"
        `when`(postCommentRepository.findById(id = dummyPostCommentId)).thenThrow(
            DataNotFoundException(
                code = ErrorCode.POST_COMMENT_NOT_FOUND,
                message = ErrorCode.POST_COMMENT_NOT_FOUND.koreanMessage
            )
        )

        // when & then
        assertThatThrownBy {
            postCommentService.getPostComment(postCommentId = dummyPostCommentId)
        }
            .isInstanceOf(DataNotFoundException::class.java)
            .hasMessage(ErrorCode.POST_COMMENT_NOT_FOUND.koreanMessage)
    }

    @Test
    fun `게시글 댓글 수정 테스트`() {
        // given
        val dummyUserId: String = dummyUser.id!!
        val dummyPostCommentId: String = dummyPostComment.id!!
        val request: UpdatePostCommentRequest = DummyPostComment.toUpdateRequest()
        `when`(postCommentRepository.findById(id = dummyPostCommentId)).thenReturn(dummyPostComment)

        `when`(
            postCommentUpdater.markAsUpdate(
                request = request,
                postComment = dummyPostComment
            )
        ).thenReturn(dummyPostComment)

        // when
        val result: String = postCommentService.updatePostComment(
            userId = dummyUserId,
            postCommentId = dummyPostCommentId,
            request = request
        )

        // then
        assertThat(result).isEqualTo(dummyPostCommentId)
        verify(postCommentValidator).validateOwner(postComment = dummyPostComment, userId = dummyUserId)
    }

    @Test
    fun `존재하지 않는 게시글 댓글 수정 시 예외 발생`() {
        // given
        val dummyUserId: String = dummyUser.id!!
        val dummyPostCommentId: String = "nonExistentCommentId"
        val request: UpdatePostCommentRequest = DummyPostComment.toUpdateRequest()
        `when`(postCommentRepository.findById(id = dummyPostCommentId)).thenThrow(
            DataNotFoundException(
                code = ErrorCode.POST_COMMENT_NOT_FOUND,
                message = ErrorCode.POST_COMMENT_NOT_FOUND.koreanMessage
            )
        )

        // when & then
        assertThatThrownBy {
            postCommentService.updatePostComment(
                userId = dummyUserId,
                postCommentId = dummyPostCommentId,
                request = request
            )
        }
            .isInstanceOf(DataNotFoundException::class.java)
            .hasMessage(ErrorCode.POST_COMMENT_NOT_FOUND.koreanMessage)
    }

    @Test
    fun `소유자가 아닌 사용자가 게시글 댓글 수정 시 예외 발생`() {
        // given
        val dummyUserId: String = "otherUserId"
        val dummyPostCommentId: String = dummyPostComment.id!!
        val request: UpdatePostCommentRequest = DummyPostComment.toUpdateRequest()
        `when`(postCommentRepository.findById(id = dummyPostCommentId)).thenReturn(dummyPostComment)
        `when`(postCommentValidator.validateOwner(postComment = dummyPostComment, userId = dummyUserId)).thenThrow(
            ForbiddenException(
                code = ErrorCode.FORBIDDEN,
                message = ErrorCode.FORBIDDEN.koreanMessage
            )
        )

        // when & then
        assertThatThrownBy {
            postCommentService.updatePostComment(
                userId = dummyUserId,
                postCommentId = dummyPostCommentId,
                request = request
            )
        }
            .isInstanceOf(ForbiddenException::class.java)
            .hasMessage(ErrorCode.FORBIDDEN.koreanMessage)
    }

    @Test
    fun `게시글 댓글 삭제 테스트`() {
        // given
        val dummyUserId: String = dummyUser.id!!
        val dummyPostCommentId: String = dummyPostComment.id!!
        `when`(postCommentRepository.findById(id = dummyPostCommentId)).thenReturn(dummyPostComment)

        // when
        val result: Boolean = postCommentService.deletePostComment(
            userId = dummyUserId,
            postCommentId = dummyPostCommentId
        )

        // then
        assertThat(result).isTrue()
        assertThat(dummyPostComment.isDeleted).isTrue()
        verify(postCommentValidator).validateOwner(postComment = dummyPostComment, userId = dummyUserId)
    }

    @Test
    fun `존재하지 않는 게시글 댓글 삭제 시 예외 발생`() {
        // given
        val dummyUserId: String = dummyUser.id!!
        val dummyPostCommentId: String = "nonExistentCommentId"
        `when`(postCommentRepository.findById(id = dummyPostCommentId)).thenThrow(
            DataNotFoundException(
                code = ErrorCode.POST_COMMENT_NOT_FOUND,
                message = ErrorCode.POST_COMMENT_NOT_FOUND.koreanMessage
            )
        )

        // when & then
        assertThatThrownBy {
            postCommentService.deletePostComment(
                userId = dummyUserId,
                postCommentId = dummyPostCommentId
            )
        }
            .isInstanceOf(DataNotFoundException::class.java)
            .hasMessage(ErrorCode.POST_COMMENT_NOT_FOUND.koreanMessage)
    }

    @Test
    fun `소유자가 아닌 사용자가 게시글 댓글 삭제 시 예외 발생`() {
        // given
        val dummyUserId: String = "otherUserId"
        val dummyPostCommentId: String = dummyPostComment.id!!
        `when`(postCommentRepository.findById(id = dummyPostCommentId)).thenReturn(dummyPostComment)
        `when`(postCommentValidator.validateOwner(postComment = dummyPostComment, userId = dummyUserId)).thenThrow(
            ForbiddenException(
                code = ErrorCode.FORBIDDEN,
                message = ErrorCode.FORBIDDEN.koreanMessage
            )
        )

        // when & then
        assertThatThrownBy {
            postCommentService.deletePostComment(
                userId = dummyUserId,
                postCommentId = dummyPostCommentId
            )
        }
            .isInstanceOf(ForbiddenException::class.java)
            .hasMessage(ErrorCode.FORBIDDEN.koreanMessage)
    }
}
