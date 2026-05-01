package com.plink.api.post.presentation.v1

import com.plink.api.post.application.PostCommentService
import com.plink.api.post.application.dto.CreatePostCommentRequest
import com.plink.core.common.extension.getUserId
import com.plink.core.common.presentation.dto.ApiResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
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
}
