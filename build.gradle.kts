// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false
    // Kotlin 2.0.0 버전부터, 루트와 모듈에 이 플러그인 추가시켜야함
    alias(libs.plugins.compose.compiler) apply false
    //Hilt
    id("com.google.dagger.hilt.android") version "2.53" apply false
    id("com.google.devtools.ksp") version "2.0.20-1.0.25" apply false
}