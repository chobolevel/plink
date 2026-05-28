package com.plink.api.post.controller

import com.plink.api.common.annotation.UserOnly
import com.plink.api.post.dto.CreatePostRequest
import com.plink.api.post.dto.PostResponse
import com.plink.api.post.dto.SearchPostRequest
import com.plink.api.post.dto.UpdatePostRequest
import com.plink.api.post.service.PostService
import com.plink.core.common.dto.ApiPagingResponse
import com.plink.core.common.dto.ApiResponse
import com.plink.core.common.dto.Paging
import com.plink.core.common.dto.PagingRequest
import com.plink.core.common.extension.getUserId
import com.plink.core.post.repository.PostQueryFilter
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@Tag(name = "Post(게시글)", description = "게시글 관리 API")
@RestController
@RequestMapping("/api/v1")
class PostController(
    private val postService: PostService
) {

    @UserOnly
    @Operation(summary = "게시글 등록 API")
    @PostMapping("/posts")
    fun createPost(
        principal: Principal,
        @Valid @RequestBody
        request: CreatePostRequest
    ): ResponseEntity<ApiResponse> {
        val result: String = postService.createPost(
            userId = principal.getUserId(),
            request = request
        )
        return ResponseEntity.ok(ApiResponse.of(data = result))
    }

    @Operation(summary = "게시글 단건 조회 API")
    @GetMapping("/posts/{postId}")
    fun getPost(
        @PathVariable
        postId: String
    ): ResponseEntity<ApiResponse> {
        val result: PostResponse = postService.getPost(postId = postId)
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

    @UserOnly
    @Operation(summary = "게시글 수정 API")
    @PatchMapping("/posts/{postId}")
    fun updatePost(
        principal: Principal,
        @PathVariable
        postId: String,
        @Valid @RequestBody
        request: UpdatePostRequest,
    ): ResponseEntity<ApiResponse> {
        val result: String = postService.updatePost(
            postId = postId,
            userId = principal.getUserId(),
            request = request
        )
        return ResponseEntity.ok(ApiResponse.of(data = result))
    }

    @UserOnly
    @Operation(summary = "게시글 삭제 API")
    @DeleteMapping("/posts/{postId}")
    fun deletePost(
        principal: Principal,
        @PathVariable
        postId: String,
    ): ResponseEntity<ApiResponse> {
        val result: Boolean = postService.deletePost(
            postId = postId,
            userId = principal.getUserId()
        )
        return ResponseEntity.ok(ApiResponse.of(data = result))
    }
}
