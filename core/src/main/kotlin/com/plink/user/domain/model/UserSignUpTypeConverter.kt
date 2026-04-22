package com.plink.user.domain.model

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class UserSignUpTypeConverter : AttributeConverter<UserSignUpType, String> {
    override fun convertToDatabaseColumn(attribute: UserSignUpType) = attribute.code
    override fun convertToEntityAttribute(dbData: String) = UserSignUpType.fromCode(dbData)
}
