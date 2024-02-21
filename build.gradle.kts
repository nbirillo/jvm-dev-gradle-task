import io.gitlab.arturbosch.detekt.Detekt

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.gradle.build.config.plugin)
    alias(libs.plugins.detekt.plugin)
}

group = "org.example"
version = "1.0-SNAPSHOT"

detekt {
    buildUponDefaultConfig = true // preconfigure defaults
    allRules = false // activate all available (even unstable) rules.
    config.setFrom("$projectDir/config/detekt.yml") // point to your custom config defining rules to run, overwriting default behavior
}

allprojects {
    apply {
        plugin(rootProject.libs.plugins.kotlin.jvm.get().pluginId)
        plugin(rootProject.libs.plugins.gradle.build.config.plugin.get().pluginId)
        plugin(rootProject.libs.plugins.detekt.plugin.get().pluginId)
//        with(rootProject.libs.plugins) {
//            listOf(kotlin.jvm, gradle.build.config.plugin, detekt.plugin).map { it.get().pluginId }.forEach(::plugin)
//        }
    }
    repositories {
        mavenCentral()
        maven {
            url = uri("https://packages.jetbrains.team/maven/p/ki/maven")
        }
        maven {
            url = uri("https://packages.jetbrains.team/maven/p/grazi/grazie-platform-public")
        }
        maven {
            url = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
        }
    }

    dependencies {
        testImplementation(rootProject.libs.kotlin.test)
        api(rootProject.libs.kinference)
        implementation(rootProject.libs.roberta.tokenizer)
    }

    tasks.test {
        useJUnitPlatform()
    }
    kotlin {
        jvmToolchain(17)
    }

    tasks.withType<Detekt>().configureEach {
        reports {
            html.required.set(true) // observe findings in your browser with structure and code snippets
            xml.required.set(true) // checkstyle like format mainly for integrations like Jenkins
            txt.required.set(true) // similar to the console output, contains issue signature to manually edit baseline files
            sarif.required.set(true) // standardized SARIF format (https://sarifweb.azurewebsites.net/) to support integrations with GitHub Code Scanning
            md.required.set(true) // simple Markdown format
        }
    }

    tasks.withType<Detekt>().forEach {
        it.onlyIf { hasProperty("runDetekt") }
    }
}