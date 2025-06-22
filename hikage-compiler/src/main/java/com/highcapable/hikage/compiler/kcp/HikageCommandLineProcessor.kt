package com.highcapable.hikage.compiler.kcp

import com.google.auto.service.AutoService
import org.jetbrains.kotlin.compiler.plugin.AbstractCliOption
import org.jetbrains.kotlin.compiler.plugin.CommandLineProcessor
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.jetbrains.kotlin.config.CompilerConfiguration

@OptIn(ExperimentalCompilerApi::class)
@AutoService(CommandLineProcessor::class)
class HikageCommandLineProcessor: CommandLineProcessor {
    override val pluginId = "hikage-compiler"
    override val pluginOptions: Collection<AbstractCliOption>
        get() = emptyList()

    override fun processOption(
        option: AbstractCliOption,
        value: String,
        configuration: CompilerConfiguration
    ) {
        super.processOption(option, value, configuration)
    }
}