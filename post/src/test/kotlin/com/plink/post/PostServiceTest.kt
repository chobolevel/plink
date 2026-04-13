package com.plink.post

import com.plink.core.presentation.dto.ApiPagingResponse
import com.plink.core.presentation.dto.Paging
import com.plink.post.application.PostService
import com.plink.post.application.dto.CreatePostRequest
import com.plink.post.application.dto.PostResponse
import com.plink.post.domain.model.Post
import com.plink.post.domain.model.PostOrderType
import com.plink.post.domain.repository.PostRepository
import com.plink.post.domain.service.PostConverter
import com.plink.post.infrastructure.persistence.PostQueryFilter
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.aot.hint.TypeReference.listOf

@DisplayName("PostService unit test")
@ExtendWith(MockitoExtension::class)
class PostServiceTest {

    private val dummyPost: Post = DummyPost.toEntity()

    private val dummyPostResponse: PostResponse = DummyPost.toResponse()

    @Mock
    private lateinit var postRepository: PostRepository

    @Mock
    private lateinit var postConverter: PostConverter

    @InjectMocks
    private lateinit var postService: PostService

    @Test
    fun `게시글 등록 테스트`() {
        // given
        val request: CreatePostRequest = DummyPost.toCreateRequest()
        `when`(postConverter.toEntity(request = request)).thenReturn(dummyPost)
        `when`(postRepository.save(post = dummyPost)).thenReturn(dummyPost)

        // when
        val result: String = postService.createPost(request = request)

        // then
        assertThat(result).isEqualTo(dummyPost.id)
    }

    @Test
    fun `게시글 목록 조회`() {
        // given
        val dummyPosts: List<Post> = listOf(dummyPost)
        val dummyPostResponses: List<PostResponse> = listOf(dummyPostResponse)
        val queryFilter = PostQueryFilter(
            userId = null,
            title = null,
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
}
