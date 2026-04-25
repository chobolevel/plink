package com.plink.core.common.presentation.validator

import com.plink.core.common.infrastructure.util.HtmlUtil
import com.plink.core.common.presentation.annotation.HtmlLength
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import org.springframework.stereotype.Component

@Component
class HtmlLengthValidator : ConstraintValidator<HtmlLength, String> {

    private var minLength: Int = 20

    override fun initialize(annotation: HtmlLength) {
        this.minLength = annotation.min
    }

    override fun isValid(
        value: String?,
        context: ConstraintValidatorContext
    ): Boolean {
        if (value.isNullOrEmpty()) return true

        val length: Int = HtmlUtil.extractText(value).length

        return length >= minLength
    }
}
