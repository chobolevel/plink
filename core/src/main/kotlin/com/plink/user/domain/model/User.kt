package com.plink.user.domain.model

import com.plink.core.domain.model.BaseEntity
import com.plink.core.infrastructure.support.TsidGenerator
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import org.hibernate.annotations.ColumnDefault
import org.hibernate.annotations.Comment
import org.hibernate.envers.Audited
import org.hibernate.envers.NotAudited

@Entity
@Table(
    name = "users",
    uniqueConstraints = [
        UniqueConstraint(
            name = "users_pk_2",
            columnNames = ["email", "sign_up_type"]
        )
    ],
    indexes = [
        Index(
            name = "users_email_sign_up_type_index",
            columnList = "email, sign_up_type"
        )
    ]
)
@Comment("회원 테이블")
@Audited
class User(
    @Column(name = "email", length = 100, nullable = false)
    @Comment("이메일")
    val email: String,

    @Column(name = "password", length = 255, nullable = true)
    @Comment("비밀번호")
    var password: String? = null,

    @Column(name = "social_id", length = 100, nullable = true)
    @Comment("소셜 아이디")
    var socialId: String? = null,

    @Convert(converter = UserSignUpTypeConverter::class)
    @Column(name = "sign_up_type", length = 20, nullable = false)
    @Comment("회원가입 유형")
    val signUpType: UserSignUpType,

    @Column(name = "nickname", length = 100, nullable = false)
    @Comment("닉네임")
    var nickname: String,

    @Convert(converter = UserRoleTypeConverter::class)
    @Column(name = "role", length = 20, nullable = false)
    @Comment("권한")
    var role: UserRoleType,

    @ColumnDefault("0")
    @Column(name = "balance", nullable = false)
    @Comment("포인트")
    var balance: Int = 0,
) : BaseEntity() {

    @Id
    @TsidGenerator
    @Column(name = "id", length = 13, nullable = false)
    @Comment("아이디")
    var id: String? = null

    @ColumnDefault("0")
    @Column(name = "is_resigned", nullable = false, columnDefinition = "TINYINT(1)")
    @Comment("탈퇴 여부")
    var isResigned: Boolean = false

    @NotAudited
    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.EAGER)
    val userPermissions: MutableSet<UserPermission> = mutableSetOf()

    fun resign() {
        this.isResigned = true
    }

    /* ==============================
     * 연관관계 편의 메서드
     * ============================== */
    fun addUserPermissions(userPermissions: List<UserPermission>) {
        userPermissions.forEach { userPermission ->
            if (userPermission !in this.userPermissions) {
                userPermission.assignUser(user = this)
                this.userPermissions.add(userPermission)
            }
        }
    }
}
