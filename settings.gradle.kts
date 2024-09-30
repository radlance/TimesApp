pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "TimesApp"
include(":app")
include(":features")
include(":features:time")
include(":features:time:presentation")
include(":core")
include(":core:uikit")
include(":features:stopwatch")
include(":features:stopwatch:presentation")
include(":features:timer")
include(":features:timer:presentation")
include(":features:foreground-time-core")
include(":features:alarm")
include(":features:alarm:presentation")
