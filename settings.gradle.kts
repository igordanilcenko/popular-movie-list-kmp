pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}

rootProject.name = "popular-movie-list-kmp"

include(":shared")
include(":shared:core:usecase")
include(":shared:core:network")
include(":shared:core:navigation")
include(":shared:core:storage")
include(":shared:feature:movies")

include(":androidApp:app")
include(":androidApp:core:ui")
include(":androidApp:core:navigation")
include(":androidApp:feature:movies")
