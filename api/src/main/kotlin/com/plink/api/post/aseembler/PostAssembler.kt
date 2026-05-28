package com.plink.api.post.assembler

import com.plink.core.post.entity.Post
import com.plink.core.user.entity.User
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
