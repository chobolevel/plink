package com.plink.user.domain.model

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class UserPermissionActionTypeConverter : AttributeConverter<UserPermissionActionType, String> {

    override fun convertToDatabaseColumn(attribute: UserPermissionActionType): String = attribute.code

    override fun convertToEntityAttribute(dbData: String): UserPermissionActionType = UserPermissionActionType.fromCode(dbData)
}
