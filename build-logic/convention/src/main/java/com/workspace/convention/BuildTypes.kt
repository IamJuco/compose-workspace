package com.workspace.convention

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.BuildType
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

internal fun Project.configureBuildTypes(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
    extensionType: ExtensionType
) {
    commonExtension.run {
        buildFeatures {
            buildConfig = true
        }
        // apiKey는 null을 가지고있으면 안됌, API 키가 없을 시 "DEFAULT_API_KEY"로 설정
        // local.properties에 저장한 API_KEY 값을 자동으로 가져와 줌
        // 만약 BASE_URL도 사용할거면 local.properties 에 저장하고 baseUrl 변수 만들어서 사용
        // val baseUrl = gradleLocalProperties(rootDir, providers).getProperty("BASE_URL") ?: "DEFAULT_BASE_URL"
        val apiKey = gradleLocalProperties(rootDir, providers).getProperty("API_KEY") ?: "DEFAULT_API_KEY"
        when (extensionType) {
            ExtensionType.APPLICATION -> {
                extensions.configure<ApplicationExtension> {
                    buildTypes {
                        debug {
                            configureDebugBuildType(apiKey)
                        }
                        create("staging") {
                            configureStagingBuildType(apiKey)
                        }
                        release {
                            configureReleaseBuildType(commonExtension, apiKey)
                        }
                    }
                }
            }
            // App 모듈을 제외한 모듈을 위한 타입 분기처리
            ExtensionType.LIBRARY -> {
                extensions.configure<LibraryExtension> {
                    buildTypes {
                        debug {
                            configureDebugBuildType(apiKey)
                        }
                        create("staging") {
                            configureStagingBuildType(apiKey)
                        }
                        release {
                            configureReleaseBuildType(commonExtension, apiKey)
                        }
                    }
                }
            }
        }
    }
}

// Debug -> 디버그 환경에서 사용하는 서버 ( 테스트용 서버 )
private fun BuildType.configureDebugBuildType(apiKey: String) {
    buildConfigField("String", "API_KEY", "\"$apiKey\"")
    buildConfigField("String", "BASE_URL", "\"DEBUG_API_URL\"")
}

// Staging -> QA 테스트용 스테이징 서버 ( 운영 전에 기능을 검증 하기위한 서버 )
private fun BuildType.configureStagingBuildType(apiKey: String) {
    buildConfigField("String", "API_KEY", "\"$apiKey\"")
    buildConfigField("String", "BASE_URL", "\"STAGING_API_URL\"")
}

// Release -> 실제 운영 환경에서 사용하는 서버 ( 실제 운영 서버 )
private fun BuildType.configureReleaseBuildType(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
    apiKey: String
) {
    buildConfigField("String", "API_KEY", "\"$apiKey\"")
    buildConfigField("String", "BASE_URL", "\"RELEASE_API_URL\"")


    isMinifyEnabled = true
    proguardFiles(
        commonExtension.getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro"
    )
}