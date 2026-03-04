package com.plink

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PlinkApplication

fun main(args: Array<String>) {
    runApplication<PlinkApplication>(*args)
}
