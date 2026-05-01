package com.plink.api.post.application.assembler

import com.plink.core.post.domain.model.Post
import com.plink.core.post.domain.model.PostComment
import com.plink.core.user.domain.model.User
import org.springframework.stereotype.Component

@Component
class PostCommentAssembler {

    fun assemble(
        postComment: PostComment,
        parentPostComment: PostComment?,
        post: Post,
        user: User
    ): PostComment {
        postComment.assignPost(post = post)
        postComment.assignUser(user = user)
        parentPostComment?.let { postComment.assignParent(postComment = parentPostComment) }
        return postComment
    }
}
