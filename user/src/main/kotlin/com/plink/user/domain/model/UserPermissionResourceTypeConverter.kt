package com.plink.user.domain.model

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class UserPermissionResourceTypeConverter : AttributeConverter<UserPermissionResourceType, String> {

    override fun convertToDatabaseColumn(attribute: UserPermissionResourceType): String = attribute.code

    override fun convertToEntityAttribute(dbData: String): UserPermissionResourceType = UserPermissionResourceType.fromCode(dbData)
}
