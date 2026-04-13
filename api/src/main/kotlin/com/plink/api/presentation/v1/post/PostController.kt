package com.plink.api.presentation.v1.post

import com.plink.api.common.annotation.AnyAuthorize
import com.plink.core.presentation.dto.ApiPagingResponse
import com.plink.core.presentation.dto.ApiResponse
import com.plink.core.presentation.dto.Paging
import com.plink.core.presentation.dto.PagingRequest
import com.plink.post.application.PostService
import com.plink.post.application.dto.CreatePostRequest
import com.plink.post.application.dto.SearchPostRequest
import com.plink.post.infrastructure.persistence.PostQueryFilter
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Post(게시글)", description = "게시글 관리 API")
@RestController
@RequestMapping("/api/v1")
class PostController(
    private val postService: PostService
) {

    @AnyAuthorize
    @Operation(summary = "게시글 등록 API")
    @PostMapping("/posts")
    fun createPost(
        @Valid @RequestBody
        request: CreatePostRequest
    ): ResponseEntity<ApiResponse> {
        val result: String = postService.createPost(request = request)
        return ResponseEntity.ok(ApiResponse.of(data = result))
    }

    @Operation(summary = "게시글 목록 조회 API")
    @GetMapping("/posts")
    fun getPosts(searchRequest: SearchPostRequest, pagingRequest: PagingRequest): ResponseEntity<ApiPagingResponse> {
        val queryFilter = PostQueryFilter(
            userId = searchRequest.userId,
            title = searchRequest.title
        )
        val paging = Paging(
            page = pagingRequest.page ?: 1,
            size = pagingRequest.size ?: 20
        )
        val result: ApiPagingResponse = postService.getPosts(
            queryFilter = queryFilter,
            paging = paging,
            orderTypes = searchRequest.orderTypes ?: emptyList()
        )
        return ResponseEntity.ok(result)
    }
}
