plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "TaskKotlin"
include(
    "module1",
    "module2"
)

