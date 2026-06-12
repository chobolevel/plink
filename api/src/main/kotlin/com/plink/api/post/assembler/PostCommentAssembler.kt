package com.plink.api.post.assembler

import com.plink.core.post.entity.Post
import com.plink.core.post.entity.PostComment
import com.plink.core.user.entity.User
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
