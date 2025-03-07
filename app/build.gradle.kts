plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.hearyou"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.hearyou"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    implementation (libs.appcompat.v170)
    implementation (libs.material.v1120)
    implementation (libs.android.lottie)
    implementation (libs.tensorflow.tensorflow.lite)
    implementation (libs.tensorflow.lite.support)

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}