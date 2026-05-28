package com.plink.core.user.entity

import com.plink.core.user.vo.UserPermissionActionType
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class UserPermissionActionTypeConverter : AttributeConverter<UserPermissionActionType, String> {

    override fun convertToDatabaseColumn(attribute: UserPermissionActionType): String = attribute.code

    override fun convertToEntityAttribute(dbData: String): UserPermissionActionType = UserPermissionActionType.fromCode(dbData)
}
