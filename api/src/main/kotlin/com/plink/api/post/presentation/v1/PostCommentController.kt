package com.plink.api.post.presentation.v1

import com.plink.api.post.application.PostCommentService
import com.plink.api.post.application.dto.CreatePostCommentRequest
import com.plink.api.post.application.dto.PostCommentResponse
import com.plink.api.post.application.dto.SearchPostCommentRequest
import com.plink.api.post.application.dto.UpdatePostCommentRequest
import com.plink.core.common.extension.getUserId
import com.plink.core.common.presentation.dto.ApiPagingResponse
import com.plink.core.common.presentation.dto.ApiResponse
import com.plink.core.common.presentation.dto.Paging
import com.plink.core.common.presentation.dto.PagingRequest
import com.plink.core.post.infrastructure.persistence.PostCommentQueryFilter
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
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

    @Operation(summary = "게시글 댓글 등록 API")
    @PreAuthorize("hasAuthority('POST_COMMENT:WRITE')")
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

    @Operation(summary = "게시글 댓글 수정 API")
    @PreAuthorize("hasAuthority('POST_COMMENT:WRITE')")
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

    @Operation(summary = "게시글 댓글 삭제 API")
    @PreAuthorize("hasAuthority('POST_COMMENT:WRITE')")
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
