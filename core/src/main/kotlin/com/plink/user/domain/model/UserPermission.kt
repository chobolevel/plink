package com.plink.user.domain.model

import com.plink.core.infrastructure.support.TsidGenerator
import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.hibernate.annotations.Comment

@Entity
@Table(
    name = "user_permissions",
    indexes = [
        Index(
            name = "user_permissions_user_id_fk",
            columnList = "user_id"
        )
    ]
)
@Comment("회원 권한 테이블")
class UserPermission(
    @Convert(converter = UserPermissionResourceTypeConverter::class)
    @Column(name = "resource", length = 40, nullable = false)
    @Comment("자원")
    var resource: UserPermissionResourceType,

    @Convert(converter = UserPermissionActionTypeConverter::class)
    @Column(name = "action", length = 40, nullable = false)
    @Comment("권한")
    var action: UserPermissionActionType
) {

    @Id
    @TsidGenerator
    @Column(name = "id", length = 13, nullable = false)
    @Comment("아이디")
    var id: String? = null

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User? = null
        protected set

    /* ==============================
     * 연관관계 편의 메서드
     * ============================== */
    fun assignUser(user: User) {
        if (this.user != user) {
            this.user = user
        }
    }
}
