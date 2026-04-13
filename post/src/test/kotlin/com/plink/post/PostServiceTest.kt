package com.plink.post

import com.plink.post.application.PostService
import com.plink.post.application.dto.CreatePostRequest
import com.plink.post.domain.model.Post
import com.plink.post.domain.repository.PostRepository
import com.plink.post.domain.service.PostConverter
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension

@DisplayName("PostService unit test")
@ExtendWith(MockitoExtension::class)
class PostServiceTest {

    private val dummyPost: Post = DummyPost.toEntity()

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
}
