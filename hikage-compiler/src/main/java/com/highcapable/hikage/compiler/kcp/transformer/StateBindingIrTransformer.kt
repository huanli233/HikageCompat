package com.highcapable.hikage.compiler.kcp.transformer

import com.highcapable.hikage.compiler.kcp.ir.isFunctionOfClass
import com.highcapable.hikage.compiler.kcp.logging.info
import com.highcapable.hikage.compiler.kcp.logging.logging
import com.highcapable.hikage.compiler.kcp.logging.output
import org.jetbrains.kotlin.backend.common.IrElementTransformerVoidWithContext
import org.jetbrains.kotlin.backend.common.extensions.FirIncompatiblePluginAPI
import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity
import org.jetbrains.kotlin.cli.common.messages.MessageCollector
import org.jetbrains.kotlin.ir.IrStatement
import org.jetbrains.kotlin.ir.declarations.IrFunction
import org.jetbrains.kotlin.ir.declarations.IrValueDeclaration
import org.jetbrains.kotlin.ir.declarations.IrVariable
import org.jetbrains.kotlin.ir.expressions.IrCall
import org.jetbrains.kotlin.ir.expressions.IrGetValue
import org.jetbrains.kotlin.ir.symbols.IrSymbol
import org.jetbrains.kotlin.ir.symbols.UnsafeDuringIrConstructionAPI
import org.jetbrains.kotlin.ir.types.isSubtypeOfClass
import org.jetbrains.kotlin.ir.util.dump
import org.jetbrains.kotlin.ir.util.fqNameWhenAvailable
import org.jetbrains.kotlin.name.CallableId
import org.jetbrains.kotlin.name.ClassId
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.name.Name

@OptIn(UnsafeDuringIrConstructionAPI::class)
class StateBindingIrTransformer(
    private val pluginContext: IrPluginContext,
    private val messageCollector: MessageCollector
): IrElementTransformerVoidWithContext() {

    private val stateClassId = ClassId.topLevel(FqName("com.highcapable.hikage.core.runtime.State"))
    private val bindCallableId = CallableId(
        packageName = FqName("com.highcapable.hikage.core"),
        className = FqName("Hikage.PerformerScope"),
        callableName = Name.identifier("bind")
    )

    private val stateClass by lazy { pluginContext.referenceClass(stateClassId)!! }
    private val bindFunction by lazy {
        pluginContext.referenceFunctions(bindCallableId).firstOrNull {
            it.owner.extensionReceiverParameter?.type?.isSubtypeOfClass(stateClass) == true
        }
    }

    private val boundVariables = mutableMapOf<IrSymbol, IrValueDeclaration>()

    override fun visitFunctionNew(declaration: IrFunction): IrStatement {
        if (declaration.isFunctionOfClass(FqName("com.highcapable.hikage.demo.ui.MainActivity"))
            && declaration.name.asString() == "onCreate") {
            messageCollector.info("visitFunctionNew: ${declaration.fqNameWhenAvailable}")
            messageCollector.info(declaration.dump())
        }
        return super.visitFunctionNew(declaration)
    }

    override fun visitVariable(declaration: IrVariable): IrStatement {
        val initializer = declaration.initializer
        if (initializer is IrCall && initializer.symbol == bindFunction) {
            val stateProvider = initializer.dispatchReceiver as? IrGetValue
            if (stateProvider != null) {
                // Flag the variable as a bound state
                boundVariables[declaration.symbol] = stateProvider.symbol.owner
            }
        }
        return super.visitVariable(declaration)
    }
}

