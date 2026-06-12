package com.plink.post

import com.plink.api.post.assembler.PostAssembler
import com.plink.api.post.converter.PostConverter
import com.plink.api.post.dto.CreatePostRequest
import com.plink.api.post.dto.PostResponse
import com.plink.api.post.dto.UpdatePostRequest
import com.plink.api.post.service.PostService
import com.plink.api.post.updater.PostUpdater
import com.plink.api.post.validator.PostValidator
import com.plink.core.common.dto.ApiPagingResponse
import com.plink.core.common.dto.Paging
import com.plink.core.common.exception.DataNotFoundException
import com.plink.core.common.exception.ErrorCode
import com.plink.core.common.exception.ForbiddenException
import com.plink.core.common.exception.InvalidParameterException
import com.plink.core.post.entity.Post
import com.plink.core.post.repository.PostQueryFilter
import com.plink.core.post.repository.PostRepository
import com.plink.core.post.vo.PostOrderType
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
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.doThrow
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension

@DisplayName("PostService unit test")
@ExtendWith(MockitoExtension::class)
class PostServiceTest {

    private val dummyPost: Post = DummyPost.toEntity()

    private val dummyUser: User = DummyUser.toEntity()

    private val dummyPostResponse: PostResponse = DummyPost.toResponse()

    @Mock
    private lateinit var postRepository: PostRepository

    @Mock
    private lateinit var userRepository: UserRepository

    @Mock
    private lateinit var postConverter: PostConverter

    @Mock
    private lateinit var postAssembler: PostAssembler

    @Mock
    private lateinit var postUpdater: PostUpdater

    @Mock
    private lateinit var postValidator: PostValidator

    @InjectMocks
    private lateinit var postService: PostService

    @Test
    fun `게시글 등록 테스트`() {
        // given
        val dummyUserId = "dummyUserId"
        val request: CreatePostRequest = DummyPost.toCreateRequest()
        `when`(postConverter.toEntity(request = request)).thenReturn(dummyPost)
        `when`(userRepository.findById(id = dummyUserId)).thenReturn(dummyUser)
        `when`(
            postAssembler.assemble(
                post = dummyPost,
                user = dummyUser
            )
        ).thenReturn(dummyPost)
        `when`(postRepository.save(post = dummyPost)).thenReturn(dummyPost)

        // when
        val result: String = postService.createPost(userId = dummyUserId, request = request)

        // then
        assertThat(result).isEqualTo(dummyPost.id)
    }

    @Test
    fun `게시글 단건 조회 성공 테스트`() {
        // given
        val postId = "dummyPostId"
        `when`(postRepository.findById(id = postId)).thenReturn(dummyPost)
        `when`(postConverter.toResponse(post = dummyPost)).thenReturn(dummyPostResponse)

        // when
        val result: PostResponse = postService.getPost(postId = postId)

        // then
        assertThat(result).isEqualTo(dummyPostResponse)
    }

    @Test
    fun `게시글 단건 조회 실패 테스트 (존재하지 않는 게시글)`() {
        // given
        val invalidPostId = "invalidPostId"
        `when`(postRepository.findById(id = invalidPostId)).thenThrow(
            DataNotFoundException(
                code = ErrorCode.POST_NOT_FOUND,
                message = ErrorCode.POST_NOT_FOUND.koreanMessage
            )
        )

        // when & then
        assertThatThrownBy { postService.getPost(postId = invalidPostId) }
            .isInstanceOf(DataNotFoundException::class.java)
            .hasMessage(ErrorCode.POST_NOT_FOUND.koreanMessage)
    }

    @Test
    fun `게시글 목록 조회`() {
        // given
        val dummyPosts: List<Post> = listOf(dummyPost)
        val dummyPostResponses: List<PostResponse> = listOf(dummyPostResponse)
        val queryFilter = PostQueryFilter(
            userId = null,
            title = null,
            isDeleted = false
        )
        val paging = Paging(
            page = 1,
            size = 20
        )
        val orderTypes: List<PostOrderType> = emptyList()
        `when`(
            postRepository.searchPosts(
                queryFilter = queryFilter,
                paging = paging,
                orderTypes = orderTypes
            )
        ).thenReturn(dummyPosts)
        `when`(
            postRepository.searchPostsCount(
                queryFilter = queryFilter
            )
        ).thenReturn(dummyPosts.size.toLong())
        `when`(postConverter.toResponseInBatch(posts = dummyPosts)).thenReturn(dummyPostResponses)

        // when
        val result: ApiPagingResponse = postService.getPosts(
            queryFilter = queryFilter,
            paging = paging,
            orderTypes = orderTypes
        )

        // then
        assertThat(result.page).isEqualTo(paging.page)
        assertThat(result.size).isEqualTo(paging.size)
        assertThat(result.data).isEqualTo(dummyPostResponses)
        assertThat(result.totalCount).isEqualTo(dummyPostResponses.size.toLong())
    }

    @Test
    fun `게시글 수정 성공 테스트`() {
        // given
        val postId = "dummyPostId"
        val userId = "dummyUserId"
        val request: UpdatePostRequest = DummyPost.toUpdateRequest()
        doNothing().`when`(postValidator).validate(request = request)
        `when`(postRepository.findById(id = postId)).thenReturn(dummyPost)
        `when`(postUpdater.markAsUpdate(request = request, post = dummyPost)).thenReturn(dummyPost)

        // when
        val result: String = postService.updatePost(postId = postId, userId = userId, request = request)

        // then
        assertThat(result).isEqualTo(dummyPost.id)
    }

    @Test
    fun `게시글 수정 실패 테스트 (파라미터 유효성 검사 실패)`() {
        // given
        val postId = "dummyPostId"
        val userId = "dummyUserId"
        val request: UpdatePostRequest = DummyPost.toUpdateRequest()
        doThrow(
            InvalidParameterException(
                code = ErrorCode.INVALID_PARAMETER,
                message = ErrorCode.INVALID_PARAMETER.koreanMessage
            )
        ).`when`(postValidator).validate(request = request)

        // when & then
        assertThatThrownBy { postService.updatePost(postId = postId, userId = userId, request = request) }
            .isInstanceOf(InvalidParameterException::class.java)
            .hasMessage(ErrorCode.INVALID_PARAMETER.koreanMessage)
    }

    @Test
    fun `게시글 수정 실패 테스트 (권한 없음)`() {
        // given
        val postId = "dummyPostId"
        val userId = "otherUserId"
        val request: UpdatePostRequest = DummyPost.toUpdateRequest()
        doNothing().`when`(postValidator).validate(request = request)
        `when`(postRepository.findById(id = postId)).thenReturn(dummyPost)
        `when`(
            postValidator.validateOwner(
                post = dummyPost,
                userId = userId
            )
        ).thenThrow(
            ForbiddenException(
                code = ErrorCode.FORBIDDEN,
                message = ErrorCode.FORBIDDEN.koreanMessage
            )
        )

        // when & then
        assertThatThrownBy { postService.updatePost(postId = postId, userId = userId, request = request) }
            .isInstanceOf(ForbiddenException::class.java)
            .hasMessage(ErrorCode.FORBIDDEN.koreanMessage)
    }

    @Test
    fun `게시글 삭제 성공 테스트`() {
        // given
        val postId = "dummyPostId"
        val userId = "dummyUserId"
        `when`(postRepository.findById(id = postId)).thenReturn(dummyPost)

        // when
        postService.deletePost(postId = postId, userId = userId)

        // then
        assertThat(dummyPost.isDeleted).isTrue()
    }

    @Test
    fun `게시글 삭제 실패 테스트 (권한 없음)`() {
        // given
        val postId = "dummyPostId"
        val userId = "otherUserId"
        `when`(postRepository.findById(id = postId)).thenReturn(dummyPost)
        `when`(
            postValidator.validateOwner(
                post = dummyPost,
                userId = userId
            )
        ).thenThrow(
            ForbiddenException(
                code = ErrorCode.FORBIDDEN,
                message = ErrorCode.FORBIDDEN.koreanMessage
            )
        )

        // when & then
        assertThatThrownBy { postService.deletePost(postId = postId, userId = userId) }
            .isInstanceOf(ForbiddenException::class.java)
            .hasMessage(ErrorCode.FORBIDDEN.koreanMessage)
    }
}
