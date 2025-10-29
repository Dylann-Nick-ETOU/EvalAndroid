

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.evaldylann.feature.movies.data"
    compileSdk = 36

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

}

dependencies {
    implementation(project(":features:movies:domain"))

    // Ktor + JSON
    implementation(libs.bundles.ktor)
    implementation(libs.kotlinx.serialization.json)

    // Room
    implementation(libs.bundles.room)
    implementation(libs.firebase.crashlytics.buildtools)
    ksp(libs.androidx.room.compiler)

    // Koin
    implementation(platform(libs.koin.bom))
    implementation(libs.bundles.koin)

}