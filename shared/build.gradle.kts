import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinSerialization)
}

// Gera ApiConfig.kt a partir do local.properties (ignorado pelo git), como um .env.
// Chave: API_BASE_URL=http://10.0.2.2:5085
val generateApiConfig by tasks.registering {
    val propsFile = rootProject.layout.projectDirectory.file("local.properties").asFile
    val outDir = layout.buildDirectory.dir("generated/apiconfig/kotlin")
    inputs.files(propsFile).optional()
    outputs.dir(outDir)
    doLast {
        val props = Properties()
        if (propsFile.exists()) propsFile.inputStream().use { props.load(it) }
        val url = props.getProperty("API_BASE_URL")?.trim().orEmpty()
        if (url.isEmpty()) {
            throw GradleException("Defina API_BASE_URL no local.properties (ex.: API_BASE_URL=http://10.0.2.2:5085)")
        }
        val file = outDir.get().asFile.resolve("com/vntransportltda/app_routexflow/ApiConfig.kt")
        file.parentFile.mkdirs()
        file.writeText(
            """
            |package com.vntransportltda.app_routexflow
            |
            |// GERADO por :shared:generateApiConfig — não editar nem versionar.
            |object ApiConfig {
            |    const val BASE_URL = "${url.replace("\\", "\\\\").replace("\"", "\\\"")}"
            |}
            |""".trimMargin(),
        )
    }
}

kotlin {
    sourceSets.commonMain { kotlin.srcDir(generateApiConfig) }
    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Shared"
            isStatic = true
        }
    }

    android {
        namespace = "com.vntransportltda.app_routexflow.shared"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()

        compilerOptions {
            jvmTarget = JvmTarget.JVM_11
        }
        androidResources {
            enable = true
        }
        withHostTest {
            isIncludeAndroidResources = true
        }
        withDeviceTestBuilder {
            sourceSetTreeName = "test"
        }.configure {
            instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.compose.uiTooling)
            implementation(libs.ktor.client.okhttp)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
        commonMain.dependencies {
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.contentNegotiation)
            implementation(libs.ktor.serialization.kotlinxJson)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

dependencies {
    androidRuntimeClasspath(libs.compose.uiTooling)
}