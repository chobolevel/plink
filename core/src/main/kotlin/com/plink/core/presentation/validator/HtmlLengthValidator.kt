package com.plink.core.presentation.validator

import com.plink.core.presentation.annotation.HtmlLength
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import org.jsoup.Jsoup
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

        val length: Int = Jsoup.parse(value)
            .text()
            .trim()
            .length

        return length >= minLength
    }
}
