package com.plink.core.common.entity

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.hibernate.envers.Audited
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.OffsetDateTime

@MappedSuperclass
@EntityListeners(value = [AuditingEntityListener::class])
@Audited
class BaseEntity {

    @Column(nullable = false)
    @CreatedDate
    lateinit var createdAt: OffsetDateTime

    @Column(nullable = false)
    @LastModifiedDate
    lateinit var updatedAt: OffsetDateTime
}
