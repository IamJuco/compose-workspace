plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    //Hilt
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    // Firebase
    alias(libs.plugins.firebase)
    // Custom Plugin
    alias(libs.plugins.workspace.android.library)
}

android {
    namespace = "com.workspace.core.data"
}

dependencies {

    implementation(project(":core:domain"))

    // Hilt
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    // Retrofit ,OkHttp3, Gson
    implementation(libs.bundles.networking)

    // Paging3
    implementation(libs.androidx.paging.runtime.ktx)

    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:33.7.0"))
    implementation("com.google.firebase:firebase-auth-ktx")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}