package com.plink.post.domain.model

import com.plink.core.domain.model.BaseEntity
import com.plink.core.infrastructure.support.TsidGenerator
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.Table
import org.hibernate.annotations.ColumnDefault
import org.hibernate.annotations.Comment
import org.hibernate.envers.Audited

@Entity
@Table(
    name = "posts",
    indexes = [
        Index(
            name = "posts_user_id_fk",
            columnList = "writer_id"
        )
    ]
)
@Comment("게시글 테이블")
@Audited
class Post(
    @Column(name = "user_id", length = 13, nullable = false)
    @Comment("작성자(회원) 아이디")
    val userId: String,

    @Column(name = "user_nickname", length = 100, nullable = false)
    @Comment("작성자(회원) 닉네임")
    var userNickname: String,

    @Column(name = "title", length = 100, nullable = false)
    @Comment("게시글 제목")
    var title: String,

    @Column(name = "content", nullable = false)
    @Comment("게시글 내용")
    var content: String,
) : BaseEntity() {

    @Id
    @TsidGenerator
    @Column(name = "id", length = 13, nullable = false)
    @Comment("아이디")
    var id: String? = null

    @Column(name = "is_deleted", nullable = false, columnDefinition = "TINYINT(1)")
    @ColumnDefault("0")
    @Comment("게시글 삭제 여부")
    var isDeleted: Boolean = false

    fun delete() {
        this.isDeleted = true
    }
}
