plugins {
    `kotlin-dsl`
}

group = "com.workspace.buildlogic"

dependencies {
    compileOnly(libs.android.gradle.plugin)
    compileOnly(libs.kotlin.gradle.plugin)
    compileOnly(libs.ksp.gradle.plugin)
    compileOnly(libs.hilt.gradle.plugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "workspace.android.application" // toml에 설정된 plugin id와 동일해야함
            implementationClass = "AndroidApplicationConventionPlugin" // Plugin Class 이름을 그대로 가져옴
        }
    }
}