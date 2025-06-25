package com.highcapable.hikage.compiler.kcp.transformer

import com.highcapable.hikage.compiler.kcp.logging.info
import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension
import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.cli.common.messages.MessageCollector
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment
import org.jetbrains.kotlin.ir.symbols.UnsafeDuringIrConstructionAPI
import org.jetbrains.kotlin.ir.util.dump
import org.jetbrains.kotlin.ir.visitors.transformChildrenVoid
import org.jetbrains.kotlin.name.CallableId
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.name.Name

class HikageIrGenerationExtension(val messageCollector: MessageCollector): IrGenerationExtension {
    @OptIn(UnsafeDuringIrConstructionAPI::class)
    override fun generate(
        moduleFragment: IrModuleFragment,
        pluginContext: IrPluginContext
    ) {
        val transformer = StateBindingIrTransformer(pluginContext, messageCollector)
        moduleFragment.transformChildrenVoid(transformer)
        pluginContext.referenceFunctions(
            CallableId(
                packageName = FqName("com.highcapable.hikage.demo.ui"),
                className = FqName("MainActivity"),
                callableName = Name.identifier("onCreate")
            )
        ).forEach {
            messageCollector.info("Found onCreate function: ${it.owner.dump()}")
        }
    }
}