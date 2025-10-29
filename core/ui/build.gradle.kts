plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.evaldylann.core.ui"
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
    buildFeatures {
        compose = true
    }
}

dependencies {

    // BOM Compose : aligne les versions des artefacts Compose entre eux
    implementation(platform(libs.androidx.compose.bom))

    // Android de base (KTX)
    implementation(libs.androidx.core.ktx)
    // Lifecycle runtime (pour collectAsStateWithLifecycle ...)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    // Intégration Activity <=> Compose (setContent ...)
    implementation(libs.androidx.activity.compose)

    // Modules Compose UI
    implementation(libs.bundles.compose.ui)

    // Outils de prévisualisation en build debug uniquement
    debugImplementation(libs.androidx.compose.ui.tooling)
}