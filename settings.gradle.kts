@file:Suppress("UnstableApiUsage")

pluginManagement {
    repositories {
        google()
        mavenCentral()
    }
    includeBuild("reactnative/node_modules/@react-native/gradle-plugin")
}
plugins {
    id("com.facebook.react.settings")
}
extensions.configure<com.facebook.react.ReactSettingsExtension> {
    autolinkLibrariesFromCommand(
        workingDirectory = settings.layout.rootDirectory.dir("reactnative").asFile,
        lockFiles = settings.layout.rootDirectory
            .dir("reactnative")
            .files("yarn.lock", "package-lock.json", "package.json", "react-native.config.js")
    )
}
rootProject.name = "ReactAndroidProjectStructureDemo"
include (":app")
includeBuild("reactnative/node_modules/@react-native/gradle-plugin")
include(":lib")
include(":app-bare")
