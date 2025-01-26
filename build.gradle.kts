// build.gradle.kts (Project Level)
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        // Android Gradle Plugin
        classpath("com.android.tools.build:gradle:8.8.0")

        // Kotlin Gradle Plugin
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
