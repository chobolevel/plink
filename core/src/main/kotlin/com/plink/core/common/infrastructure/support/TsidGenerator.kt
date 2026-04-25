package com.plink.core.common.infrastructure.support

import org.hibernate.annotations.IdGeneratorType

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
@IdGeneratorType(TsidIdentifierGenerator::class)
annotation class TsidGenerator(val prefix: String = "")
