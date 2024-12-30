import com.android.build.api.dsl.LibraryExtension
import com.workspace.convention.addUILayerDependencies
import com.workspace.convention.configureAndroidCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

// 화면이 존재하는 Compose Library 모듈을 위한 Plugin
class AndroidPresentationUIConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                // compose library 대신 일반 library 추가
                apply("workspace.android.library")
            }

            val extension = extensions.getByType<LibraryExtension>()
            configureAndroidCompose(extension)

            //TODO 현재 addUILayerDependencies을 강제로 추가하면 문제 발생
//            dependencies {
//                addUILayerDependencies(target)
//            }
        }
    }
}