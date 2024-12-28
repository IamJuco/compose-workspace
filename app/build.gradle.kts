plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    // Kotlin 2.0.0 버전부터, 루트와 모듈에 이 플러그인 추가시켜야함
    alias(libs.plugins.compose.compiler)
    //Hilt
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    // Firebase
    alias(libs.plugins.firebase)
    // Custom Plugin
    alias(libs.plugins.workspace.android.application)
    alias(libs.plugins.workspace.android.application.compose)
}

android {
    namespace = "com.workspace.app"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":feature:home"))
    implementation(project(":feature:mypage"))
    implementation(project(":feature:detail"))
    implementation(project(":feature:login"))
    implementation(project(":feature:signup"))

    implementation(project(":core:data"))
    implementation(project(":core:domain"))

    // Navigation
    implementation(libs.androidx.navigation.compose)

    // Hilt
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:33.7.0"))
    implementation("com.google.firebase:firebase-auth-ktx")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}