package com.workspace.convention

import org.gradle.api.Project
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.project

// 현재 강제로 의존성을 주입중이기에 임시로 사용 안함
// 디자인시스템이 있을떄 사용하면 좋을듯
fun DependencyHandlerScope.addUILayerDependencies(project: Project) {
    add("implementation", project(":core:domain"))

    add("implementation", project.libs.findBundle("compose").get())
    add("debugImplementation", project.libs.findBundle("compose.debug").get())
    add("androidTestImplementation", project.libs.findLibrary("androidx.ui.test.junit4").get())
}