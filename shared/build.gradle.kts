import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.kotlin.multiplatform)
}

kotlin {
    androidLibrary {
        namespace = "com.ihardanilchanka.sampleappkmp.shared"
        compileSdk = (property("sdk.compile") as String).toInt()
        minSdk = (property("sdk.min") as String).toInt()
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Shared"
            isStatic = true

            export(project(":shared:core:usecase"))
            export(project(":shared:core:network"))
            export(project(":shared:core:navigation"))
            export(project(":shared:core:storage"))
            export(project(":shared:feature:movies"))
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.koin.core)

            api(project(":shared:core:usecase"))
            api(project(":shared:core:network"))
            api(project(":shared:core:navigation"))
            api(project(":shared:core:storage"))
            api(project(":shared:feature:movies"))
        }

        androidMain.dependencies {
            implementation(libs.koin.android)
            implementation(libs.multiplatform.settings)
        }

        iosMain.dependencies {
            implementation(libs.multiplatform.settings)
        }
    }
}
