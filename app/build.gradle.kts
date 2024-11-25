plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    // Kotlin 2.0.0 버전부터, 루트와 모듈에 이 플러그인 추가시켜야함
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.workspace.app"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.workspace.main"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":feature:main"))
    implementation(project(":feature:home"))

    implementation(project(":core:data"))
    implementation(project(":core:domain"))

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
}