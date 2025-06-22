package com.highcapable.hikage.compiler

import com.huanli233.hikage.compat.generated.HikageCompilerProperties
import org.gradle.api.provider.Provider
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilerPluginSupportPlugin
import org.jetbrains.kotlin.gradle.plugin.SubpluginArtifact
import org.jetbrains.kotlin.gradle.plugin.SubpluginOption

class HikageIRPlugin: KotlinCompilerPluginSupportPlugin {
    override fun applyToCompilation(kotlinCompilation: KotlinCompilation<*>): Provider<List<SubpluginOption>> {
        return kotlinCompilation.target.project.provider { emptyList() }
    }

    override fun getCompilerPluginId(): String {
        return "com.highcapable.hikage.compiler"
    }

    override fun getPluginArtifact() = SubpluginArtifact(
        HikageCompilerProperties.PROJECT_GROUP_NAME,
        "hikage-compiler",
        HikageCompilerProperties.PROJECT_HIKAGE_COMPILER_VERSION
    )

    override fun isApplicable(kotlinCompilation: KotlinCompilation<*>): Boolean = true
}