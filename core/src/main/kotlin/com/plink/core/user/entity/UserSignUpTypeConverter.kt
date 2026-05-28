package com.plink.core.user.entity

import com.plink.core.user.vo.UserSignUpType
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class UserSignUpTypeConverter : AttributeConverter<UserSignUpType, String> {
    override fun convertToDatabaseColumn(attribute: UserSignUpType) = attribute.code
    override fun convertToEntityAttribute(dbData: String) = UserSignUpType.fromCode(dbData)
}
