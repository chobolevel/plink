plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
rootProject.name = "plink"
include("core")
include("user")
include("api")
include("jpa-envers")
