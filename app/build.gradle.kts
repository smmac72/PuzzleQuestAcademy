import com.android.build.api.dsl.Packaging

// app/build.gradle.kts (Module Level)
plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("com.google.gms.google-services")
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.21"
}

android {
    compileSdk = 35
    namespace = "com.fromzero.puzzlequestacademy" // Added namespace

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
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

    composeOptions {
        kotlinCompilerExtensionVersion = property("composeVersion") as String
    }
}

dependencies {
    // Retrieve version variables from gradle.properties
    val composeVersion: String by project
    val hiltVersion: String by project
    val firebaseBomVersion: String by project
    val kotlinVersion: String by project

    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.7")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.20")
    implementation("com.google.android.gms:play-services-auth:21.3.0")
    implementation("androidx.compose.runtime:runtime-livedata:1.7.6")

    // Kotlin Standard Library
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")

    // Hilt for Dependency Injection
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    implementation(libs.androidx.material3.android)
    kapt("com.google.dagger:hilt-android-compiler:$hiltVersion")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    // Room for Database
    implementation("androidx.room:room-runtime:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")

    // Firebase Dependencies
    implementation(platform("com.google.firebase:firebase-bom:$firebaseBomVersion"))
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-firestore-ktx")

    // Jetpack Compose
    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.material:material:$composeVersion")
    implementation("androidx.compose.ui:ui-tooling-preview:$composeVersion")
    debugImplementation("androidx.compose.ui:ui-tooling:$composeVersion")

    // Navigation Compose
    implementation("androidx.navigation:navigation-compose:2.8.5")

    // Lifecycle Components
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // In-App Purchases (Optional)
    implementation("com.android.billingclient:billing-ktx:7.1.1")

    // Multidex Support
    implementation("androidx.multidex:multidex:2.0.1")

    // Testing Libraries
    testImplementation(libs.junit)
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$composeVersion")
}
// Removed the following line to avoid conflicts
// apply(plugin = "com.google.gms.google-services")
