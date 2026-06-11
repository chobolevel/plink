package com.plink.core.post.entity

import com.plink.core.common.entity.BaseEntity
import com.plink.core.common.support.TsidGenerator
import com.plink.core.user.entity.User
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.hibernate.annotations.ColumnDefault
import org.hibernate.annotations.Comment
import org.hibernate.envers.Audited

@Entity
@Table(
    name = "post_comments",
    indexes = [
        Index(
            name = "post_comments_parent_id_index",
            columnList = "parent_id"
        ),
        Index(
            name = "post_comments_post_id_fk",
            columnList = "post_id"
        ),
        Index(
            name = "post_comments_user_id_fk",
            columnList = "user_id"
        )
    ]
)
@Comment("게시글 댓글 테이블")
@Audited
class PostComment(
    @Column(name = "content", length = 255, nullable = false)
    @Comment("게시글 댓글 내용")
    var content: String,
) : BaseEntity() {

    @Id
    @TsidGenerator
    @Column(name = "id", length = 13, nullable = false)
    @Comment("아이디")
    var id: String? = null

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    var post: Post? = null

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User? = null

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    var parent: PostComment? = null

    @Column(name = "is_deleted", nullable = false, columnDefinition = "TINYINT(1)")
    @ColumnDefault("0")
    @Comment("게시글 삭제 여부")
    var isDeleted: Boolean = false

    fun assignPost(post: Post) {
        if (this.post != post) {
            this.post = post
        }
    }

    fun assignUser(user: User) {
        if (this.user != user) {
            this.user = user
        }
    }

    fun assignParent(postComment: PostComment) {
        if (this != postComment && this.parent != postComment) {
            this.parent = postComment
        }
    }

    fun delete() {
        this.isDeleted = true
    }
}
