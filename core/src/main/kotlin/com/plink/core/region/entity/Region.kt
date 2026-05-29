package com.plink.core.region.entity

import com.plink.core.common.entity.BaseEntity
import com.plink.core.common.support.TsidGenerator
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

@Entity
@Table(
    name = "regions",
    indexes = [
        Index(
            name = "regions_parent_id_index",
            columnList = "parent_id"
        )
    ]
)
@Comment("지역 테이블")
class Region(
    @Column(name = "name", nullable = false, length = 100)
    var name: String,
    @Column(name = "sort_order", nullable = false)
    var sortOrder: Int
) : BaseEntity() {

    @Id
    @TsidGenerator
    @Column(name = "id", length = 13, nullable = false)
    @Comment("아이디")
    var id: String? = null

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    var parent: Region? = null

    @Column(name = "is_deleted", nullable = false, columnDefinition = "TINYINT(1)")
    @ColumnDefault("0")
    var isDeleted: Boolean = false

    fun assignParent(region: Region) {
        if (this.parent != region) {
            this.parent = region
        }
    }

    fun delete() {
        this.isDeleted = true
    }
}
