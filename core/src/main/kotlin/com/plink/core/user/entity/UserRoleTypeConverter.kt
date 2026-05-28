package com.plink.core.user.entity

import com.plink.core.user.vo.UserRoleType
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class UserRoleTypeConverter : AttributeConverter<UserRoleType, String> {

    override fun convertToDatabaseColumn(attribute: UserRoleType): String = attribute.code

    override fun convertToEntityAttribute(dbData: String): UserRoleType = UserRoleType.fromCode(dbData)
}
