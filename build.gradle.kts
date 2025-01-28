// build.gradle.kts (Project Level)
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        // Use the AGP version from gradle.properties if you wish (uncomment below).
        // classpath("com.android.tools.build:gradle:${property("android_gradle_plugin_version")}")

        // Or pin it explicitly to match your pluginManagement block:
        classpath("com.android.tools.build:gradle:8.8.0")

        // Kotlin Gradle Plugin (use the version from gradle.properties, i.e. 2.0.21)
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${property("kotlinVersion")}")

        // Hilt Gradle Plugin
        classpath("com.google.dagger:hilt-android-gradle-plugin:${property("hiltVersion")}")

        // Google Services Plugin
        classpath("com.google.gms:google-services:${property("google_services_plugin_version")}")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}
