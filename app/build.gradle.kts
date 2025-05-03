plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    kotlin("kapt")
}

android {
    namespace = "com.example.myapplication"
    compileSdk = 35

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        applicationId = "com.example.myapplication"
        minSdk = 25
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val apiKey: String = project.findProperty("API_KEY") as? String ?: ""
        buildConfigField("String", "API_KEY", "\"$apiKey\"")
        println("Loaded API_KEY: $apiKey")
    }

    // Configure Kotlin and Java compatibility
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8 // Set Java source compatibility to 1.8
        targetCompatibility = JavaVersion.VERSION_1_8 // Set Java target compatibility to 1.8
    }

    kotlinOptions {
        jvmTarget = "1.8" // Ensure Kotlin targets JVM 1.8
    }
}



dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.runtime.android)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.foundation)
    implementation(libs.androidx.compose.material)
    implementation(libs.coil.compose.v240)
    implementation (libs.kotlinx.coroutines.android.v160)

    // Add the Accompanist Pager library
    implementation(libs.accompanist.pager)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.androidx.benchmark.macro)

    // Room Database library
    implementation(libs.androidx.room.runtime)
    kapt(libs.androidx.room.compiler) // Add this line for Room compiler

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

