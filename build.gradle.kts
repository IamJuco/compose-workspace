// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    // Kotlin 2.0.0 버전부터, 루트와 모듈에 이 플러그인 추가시켜야함
    alias(libs.plugins.compose.compiler) apply false
}