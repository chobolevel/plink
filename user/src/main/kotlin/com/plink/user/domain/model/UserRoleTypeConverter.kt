package com.plink.user.domain.model

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class UserRoleTypeConverter : AttributeConverter<UserRoleType, String> {

    override fun convertToDatabaseColumn(attribute: UserRoleType): String = attribute.code

    override fun convertToEntityAttribute(dbData: String): UserRoleType = UserRoleType.fromCode(dbData)
}
