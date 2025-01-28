// app/build.gradle.kts
plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("com.google.gms.google-services")
    // This plugin adds Compose-specific checks; pinned to the same Kotlin major
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.21"
}

android {
    namespace = "com.fromzero.puzzlequestacademy"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.fromzero.puzzlequestacademy"
        minSdk = 23
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        multiDexEnabled = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
    }

    // Use composeVersion from gradle.properties
    composeOptions {
        kotlinCompilerExtensionVersion = property("composeVersion") as String
    }
}

dependencies {
    // Read versions from gradle.properties
    val composeVersion: String by project
    val hiltVersion: String by project
    val firebaseBomVersion: String by project
    val kotlinVersion: String by project

    // Version catalog examples (libs.* comes from libs.versions.toml)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.junit) // for local tests if needed
    //implementation(libs.play.services.auth)

    implementation("com.google.android.gms:play-services-auth:21.3.0")

    // Compose BOM approach or direct version usage:
    // Using direct version from composeVersion property:
    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.ui:ui-tooling-preview:$composeVersion")
    debugImplementation("androidx.compose.ui:ui-tooling:$composeVersion")
    implementation("androidx.compose.material:material:$composeVersion")
    implementation("androidx.compose.runtime:runtime-livedata:$composeVersion")

    // Compose Material 3 if needed from version-catalog:
    implementation(libs.androidx.material3.android)

    // Kotlin Standard Library (matching your kotlinVersion = 2.0.21)
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")

    // Hilt for Dependency Injection
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    kapt("com.google.dagger:hilt-android-compiler:$hiltVersion")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    // Room
    implementation("androidx.room:room-runtime:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")

    // Firebase BOM & typical services
    implementation(platform("com.google.firebase:firebase-bom:$firebaseBomVersion"))
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation("com.google.android.gms:play-services-auth:21.3.0")

    // In-App Purchases (optional)
    implementation("com.android.billingclient:billing-ktx:7.1.1")

    // Multidex
    implementation("androidx.multidex:multidex:2.0.1")

    // Testing
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$composeVersion")
}
