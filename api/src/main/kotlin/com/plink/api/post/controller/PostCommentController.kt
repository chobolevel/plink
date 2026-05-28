package com.plink.api.post.controller

import com.plink.api.common.annotation.UserOnly
import com.plink.api.post.dto.CreatePostCommentRequest
import com.plink.api.post.dto.PostCommentResponse
import com.plink.api.post.dto.SearchPostCommentRequest
import com.plink.api.post.dto.UpdatePostCommentRequest
import com.plink.api.post.service.PostCommentService
import com.plink.core.common.dto.ApiPagingResponse
import com.plink.core.common.dto.ApiResponse
import com.plink.core.common.dto.Paging
import com.plink.core.common.dto.PagingRequest
import com.plink.core.common.extension.getUserId
import com.plink.core.post.repository.PostCommentQueryFilter
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

@Tag(name = "PostComment(게시글 댓글)", description = "게시글 댓글 관리 API")
@RestController
@RequestMapping("/api/v1/posts")
class PostCommentController(
    private val postCommentService: PostCommentService
) {

    @UserOnly
    @Operation(summary = "게시글 댓글 등록 API")
    @PostMapping("/{postId}/comments")
    fun createPostComment(
        principal: Principal,
        @PathVariable postId: String,
        @Valid @RequestBody
        request: CreatePostCommentRequest
    ): ResponseEntity<ApiResponse> {
        val result: String = postCommentService.createPostComment(
            userId = principal.getUserId(),
            postId = postId,
            request = request
        )
        return ResponseEntity.ok(ApiResponse.of(data = result))
    }

    @Operation(summary = "게시글 댓글 목록 조회 API")
    @GetMapping("/{postId}/comments")
    fun getPostComments(
        @PathVariable postId: String,
        searchRequest: SearchPostCommentRequest,
        pagingRequest: PagingRequest
    ): ResponseEntity<ApiResponse> {
        val queryFilter = PostCommentQueryFilter(
            postId = postId,
            userId = searchRequest.userId,
            parentId = searchRequest.parentId,
        )
        val paging = Paging(
            page = pagingRequest.page ?: 1,
            size = pagingRequest.size ?: 10
        )
        val result: ApiPagingResponse = postCommentService.getPostComments(
            queryFilter = queryFilter,
            paging = paging,
            orderTypes = searchRequest.orderTypes ?: emptyList()
        )
        return ResponseEntity.ok(ApiResponse.of(data = result))
    }

    @Operation(summary = "게시글 댓글 단건 조회 API")
    @GetMapping("/{postId}/comments/{postCommentId}")
    fun getPostComment(
        @PathVariable postId: String,
        @PathVariable postCommentId: String
    ): ResponseEntity<ApiResponse> {
        val result: PostCommentResponse = postCommentService.getPostComment(postCommentId = postCommentId)
        return ResponseEntity.ok(ApiResponse.of(data = result))
    }

    @UserOnly
    @Operation(summary = "게시글 댓글 수정 API")
    @PatchMapping("/{postId}/comments/{postCommentId}")
    fun updatePostComment(
        principal: Principal,
        @PathVariable postId: String,
        @PathVariable postCommentId: String,
        @Valid @RequestBody
        request: UpdatePostCommentRequest
    ): ResponseEntity<ApiResponse> {
        val result: String = postCommentService.updatePostComment(
            userId = principal.getUserId(),
            postCommentId = postCommentId,
            request = request
        )
        return ResponseEntity.ok(ApiResponse.of(data = result))
    }

    @UserOnly
    @Operation(summary = "게시글 댓글 삭제 API")
    @DeleteMapping("/{postId}/comments/{postCommentId}")
    fun deletePostComment(
        principal: Principal,
        @PathVariable postId: String,
        @PathVariable postCommentId: String
    ): ResponseEntity<ApiResponse> {
        val result: Boolean = postCommentService.deletePostComment(
            userId = principal.getUserId(),
            postCommentId = postCommentId
        )
        return ResponseEntity.ok(ApiResponse.of(data = result))
    }
}
