plugins {
    kotlin("multiplatform")
    id("com.rickclephas.kmp.nativecoroutines") version "1.0.0-ALPHA-10"
    id("com.google.devtools.ksp") version "1.8.21-1.0.11"
    kotlin("native.cocoapods")
    id("com.android.library")
    kotlin("plugin.serialization") version "1.7.10"
}

kotlin {
    android()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "shared"
        }
    }

    kotlin.targets.withType(org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget::class.java) {
        binaries.withType(org.jetbrains.kotlin.gradle.plugin.mpp.Framework::class.java).all {
            export("dev.icerock.moko:mvvm-core:0.13.1")
        }
    }

    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    sourceSets.all {
        languageSettings.optIn("kotlin.experimental.ExperimentalObjCName")
    }

    val ktorVersion = "2.1.0"
    val coroutinesVersion = "1.6.4"
    val koinVersion = "3.1.2"
    val koinCoreVersion = "3.1.4"
    val mokoMvvmVersion = "0.13.1"

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
                implementation("io.ktor:ktor-client-core:$ktorVersion")
                implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
                implementation("io.insert-koin:koin-core:$koinCoreVersion")
                implementation("io.ktor:ktor-client-json:$ktorVersion")
                implementation("io.ktor:ktor-client-logging:$ktorVersion")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
                implementation("androidx.datastore:datastore-preferences-core:1.1.0-alpha04")
                implementation("androidx.datastore:datastore-core-okio:1.1.0-alpha04")
                api("dev.icerock.moko:mvvm-core:$mokoMvvmVersion")
            }
        }

        val androidMain by getting {
            dependencies {
                implementation("io.insert-koin:koin-androidx-compose:$koinVersion")
                implementation("io.ktor:ktor-client-android:$ktorVersion")
            }

        }
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependencies {
                implementation("io.ktor:ktor-client-ios:$ktorVersion")
            }
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting

    }
}

android {
    namespace = "com.vpex.kmm.app"
    compileSdk = 33
    defaultConfig {
        minSdk = 28
    }
}