package com.highcapable.hikage.compiler.kcp.transformer

import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension
import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.cli.common.messages.MessageCollector
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment
import org.jetbrains.kotlin.ir.visitors.transformChildrenVoid

class HikageIrGenerationExtension(val messageCollector: MessageCollector): IrGenerationExtension {
    override fun generate(
        moduleFragment: IrModuleFragment,
        pluginContext: IrPluginContext
    ) {
        val transformer = StateBindingIrTransformer(pluginContext, messageCollector)
        moduleFragment.transformChildrenVoid(transformer)
    }
}