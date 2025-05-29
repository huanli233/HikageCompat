enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
plugins {
    id("com.highcapable.sweetdependency") version "1.0.4"
    id("com.highcapable.sweetproperty") version "1.0.5"
}
sweetProperty {
    rootProject { all { isEnable = false } }
    project(
        ":hikage-core",
        ":hikage-core-lint",
        ":hikage-extension",
        ":hikage-extension-betterandroid",
        ":hikage-extension-compose",
        ":hikage-compiler",
        ":hikage-widget-androidx",
        ":hikage-widget-material"
    ) {
        sourcesCode {
            isEnableRestrictedAccess = true
        }
    }
}
rootProject.name = "Hikage"
include(":samples:app")
include(
    ":hikage-core",
    ":hikage-core-lint",
    ":hikage-extension",
    ":hikage-extension-betterandroid",
    ":hikage-extension-compose",
    ":hikage-compiler",
    ":hikage-widget-androidx",
    ":hikage-widget-material"
)