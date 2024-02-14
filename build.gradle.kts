plugins {
    id(libs.plugins.kotlin.jvm.get().pluginId) version libs.versions.kotlin.get()
    id(libs.plugins.gradle.build.config.plugin.get().pluginId) version libs.versions.gradle.build.config.get()
    id(libs.plugins.detekt.plugin.get().pluginId) version libs.versions.detekt.get()
}

group = "org.example"
version = "1.0-SNAPSHOT"

allprojects {
    apply {
        plugin("kotlin")
    }
    repositories {
        mavenCentral()
    }

    dependencies {
        testImplementation(rootProject.libs.kotlin.test)
    }

    tasks.test {
        useJUnitPlatform()
    }
    kotlin {
        jvmToolchain(17)
    }
}