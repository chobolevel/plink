package com.plink.core.generator

import org.hibernate.annotations.IdGeneratorType

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
@IdGeneratorType(TsidIdentifierGenerator::class)
annotation class TsidGenerator(val prefix: String = "")
