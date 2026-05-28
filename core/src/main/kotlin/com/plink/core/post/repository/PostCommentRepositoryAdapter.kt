package com.plink.core.post.repository

import com.plink.core.common.dto.Paging
import com.plink.core.common.exception.DataNotFoundException
import com.plink.core.common.exception.ErrorCode
import com.plink.core.post.entity.PostComment
import com.plink.core.post.entity.QPostComment.postComment
import com.plink.core.post.vo.PostCommentOrderType
import com.querydsl.core.types.OrderSpecifier
import org.springframework.stereotype.Component

@Component
class PostCommentRepositoryAdapter(
    private val postCommentJpaRepository: PostCommentJpaRepository,
    private val postCommentQuerydslRepository: PostCommentQueryDslRepository
) : PostCommentRepository {

    override fun save(postComment: PostComment): PostComment {
        return postCommentJpaRepository.save(postComment)
    }

    override fun findById(id: String): PostComment {
        return postCommentJpaRepository.findByIdAndIsDeletedFalse(id) ?: throw DataNotFoundException(
            code = ErrorCode.POST_COMMENT_NOT_FOUND,
            message = ErrorCode.POST_COMMENT_NOT_FOUND.koreanMessage
        )
    }

    override fun searchPostComments(
        queryFilter: PostCommentQueryFilter,
        paging: Paging,
        orderTypes: List<PostCommentOrderType>
    ): List<PostComment> {
        return postCommentQuerydslRepository.searchPostComments(
            booleanExpressions = queryFilter.toBooleanExpressions(),
            paging = paging,
            orderSpecifiers = orderTypes.toOrderSpecifiers()
        )
    }

    override fun searchPostCommentsCount(queryFilter: PostCommentQueryFilter): Long {
        return postCommentQuerydslRepository.searchPostCommentsCount(
            booleanExpressions = queryFilter.toBooleanExpressions(),
        )
    }

    private fun List<PostCommentOrderType>.toOrderSpecifiers(): Array<OrderSpecifier<*>> {
        return this.map {
            when (it) {
                PostCommentOrderType.CREATED_AT_ASC -> postComment.createdAt.asc()
                PostCommentOrderType.CREATED_AT_DESC -> postComment.createdAt.desc()
            }
        }.toTypedArray()
    }
}
