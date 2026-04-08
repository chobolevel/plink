package com.plink.api

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig

fun main() {
    val encryptor = PooledPBEStringEncryptor()
    val config = SimpleStringPBEConfig()
    config.password = "chobolevel-plink" // 설정 파일에 사용한 암호화 키
    config.algorithm = "PBEWithMD5AndDES"
    config.setKeyObtentionIterations("1000")
    config.setPoolSize("1")
    config.providerName = "SunJCE"
    config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator")
    config.stringOutputType = "base64"
    encryptor.setConfig(config)

    val encryptedText = encryptor.encrypt("8ZOLnua4S97wvFM9jcHY30yyZOIkxkmoDB6nhsx7+2M=")
    println("Encrypted Text: $encryptedText")
    println("Decrypted Text: ${encryptor.decrypt("VZ7OOIVlXTNg24ejE1ccGEnw5QA5HhnBkj7mwBm5pQE=")}")
}
