package com.plink.core.presentation.annotation

import com.plink.core.presentation.validator.HtmlLengthValidator
import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [HtmlLengthValidator::class])
annotation class HtmlLength(
    val min: Int,
    val message: String = "내용은 최소 20자 이상이어야 합니다.",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)
