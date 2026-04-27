package com.plink.api.post.application.assembler

import com.plink.core.post.domain.model.Post
import com.plink.core.user.domain.model.User
import org.springframework.stereotype.Component

@Component
class PostAssembler {

    fun assemble(
        post: Post,
        user: User
    ): Post {
        post.assignUser(user = user)
        return post
    }
}
