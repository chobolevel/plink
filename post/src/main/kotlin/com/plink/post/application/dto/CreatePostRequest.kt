package com.plink.post.application.dto

import com.plink.core.presentation.annotation.HtmlLength
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size

data class CreatePostRequest(
    @field:NotEmpty(message = "작성자(회원) 아이디는 필수 값입니다.")
    val userId: String,
    @field:NotEmpty(message = "작성자(회원) 닉네임은 필수 값입니다.")
    var userNickname: String,
    @field:NotEmpty(message = "게시글 제목은 필수 값입니다.")
    @field:Size(min = 10, message = "게시글 제목은 최소 10자 이상이어야 합니다.")
    val title: String,
    @field:NotEmpty(message = "게시글 내용은 필수 값입니다.")
    @field:HtmlLength(min = 20, message = "게시글 내용은 최소 20자 이상이어야 합니다.")
    val content: String,
)
