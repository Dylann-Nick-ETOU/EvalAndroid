plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.evaldylann"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.evaldylann"
        minSdk = 29
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions { jvmTarget = "11" }

    buildFeatures { compose = true }

    packaging {
        resources.excludes += setOf(
            "dump_syms/linux/**",
            "META-INF/*.kotlin_module"
        )
    }
}

dependencies {
    // Modules internes
    implementation(project(":core:ui"))
    implementation(project(":features:movies:domain"))
    implementation(project(":features:movies:data"))
    implementation(project(":features:movies:ui"))


    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.bundles.compose.ui)
    debugImplementation(libs.androidx.compose.ui.tooling)


    implementation(libs.androidx.navigation.compose)


    implementation(platform(libs.koin.bom))
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)


    implementation(libs.bundles.ktor)
    implementation(libs.kotlinx.serialization.json)


    implementation(libs.androidx.room.runtime)

}
