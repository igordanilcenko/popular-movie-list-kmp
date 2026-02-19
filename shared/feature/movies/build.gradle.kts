import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.sqldelight)
}

kotlin {
    androidLibrary {
        namespace = "com.ihardanilchanka.sampleappkmp.shared.feature.movies"
        compileSdk = (property("sdk.compile") as String).toInt()
        minSdk = (property("sdk.min") as String).toInt()
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
        withHostTest {}
    }

    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            implementation(project(":shared:core:usecase"))
            implementation(project(":shared:core:network"))
            implementation(project(":shared:core:navigation"))
            implementation(project(":shared:core:storage"))

            implementation(libs.sqldelight.runtime)
            implementation(libs.coroutines.core)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.datetime)
            implementation(libs.multiplatform.settings)
            implementation(libs.koin.core)
        }

        getByName("androidHostTest").dependencies {
            implementation(libs.junit)
            implementation(libs.kotlin.test)
            implementation(libs.mockk)
            implementation(libs.coroutines.test)
            implementation(libs.sqldelight.sqlite.driver)
        }
    }
}

sqldelight {
    databases {
        create("MoviesDatabase") {
            packageName.set("com.ihardanilchanka.sampleappkmp.data.database")
        }
    }
}
