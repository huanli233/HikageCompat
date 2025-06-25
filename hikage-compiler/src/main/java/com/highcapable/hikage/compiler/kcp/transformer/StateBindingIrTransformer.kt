@file:OptIn(UnsafeDuringIrConstructionAPI::class)

package com.highcapable.hikage.compiler.kcp.transformer

import com.highcapable.hikage.compiler.kcp.logging.info
import org.jetbrains.kotlin.backend.common.IrElementTransformerVoidWithContext
import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder
import org.jetbrains.kotlin.backend.jvm.codegen.isExtensionFunctionType
import org.jetbrains.kotlin.builtins.StandardNames
import org.jetbrains.kotlin.cli.common.messages.MessageCollector
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities
import org.jetbrains.kotlin.ir.IrElement
import org.jetbrains.kotlin.ir.IrStatement
import org.jetbrains.kotlin.ir.UNDEFINED_OFFSET
import org.jetbrains.kotlin.ir.builders.declarations.addValueParameter
import org.jetbrains.kotlin.ir.builders.declarations.buildFun
import org.jetbrains.kotlin.ir.builders.irBlockBody
import org.jetbrains.kotlin.ir.builders.irCall
import org.jetbrains.kotlin.ir.builders.irGet
import org.jetbrains.kotlin.ir.declarations.IrAnnotationContainer
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin
import org.jetbrains.kotlin.ir.declarations.IrParameterKind
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction
import org.jetbrains.kotlin.ir.declarations.IrValueDeclaration
import org.jetbrains.kotlin.ir.declarations.IrVariable
import org.jetbrains.kotlin.ir.expressions.IrBlock
import org.jetbrains.kotlin.ir.expressions.IrBlockBody
import org.jetbrains.kotlin.ir.expressions.IrCall
import org.jetbrains.kotlin.ir.expressions.IrComposite
import org.jetbrains.kotlin.ir.expressions.IrExpression
import org.jetbrains.kotlin.ir.expressions.IrFunctionExpression
import org.jetbrains.kotlin.ir.expressions.IrGetValue
import org.jetbrains.kotlin.ir.expressions.IrLoop
import org.jetbrains.kotlin.ir.expressions.IrSetValue
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin
import org.jetbrains.kotlin.ir.expressions.IrStringConcatenation
import org.jetbrains.kotlin.ir.expressions.IrVararg
import org.jetbrains.kotlin.ir.expressions.IrWhen
import org.jetbrains.kotlin.ir.expressions.impl.IrConstructorCallImpl
import org.jetbrains.kotlin.ir.expressions.impl.IrFunctionExpressionImpl
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl
import org.jetbrains.kotlin.ir.expressions.putArgument
import org.jetbrains.kotlin.ir.symbols.IrVariableSymbol
import org.jetbrains.kotlin.ir.symbols.UnsafeDuringIrConstructionAPI
import org.jetbrains.kotlin.ir.types.IrSimpleType
import org.jetbrains.kotlin.ir.types.IrType
import org.jetbrains.kotlin.ir.types.addAnnotations
import org.jetbrains.kotlin.ir.types.typeOrFail
import org.jetbrains.kotlin.ir.types.typeOrNull
import org.jetbrains.kotlin.ir.types.typeWith
import org.jetbrains.kotlin.ir.util.SYNTHETIC_OFFSET
import org.jetbrains.kotlin.ir.util.classId
import org.jetbrains.kotlin.ir.util.constructors
import org.jetbrains.kotlin.ir.util.dump
import org.jetbrains.kotlin.ir.util.hasAnnotation
import org.jetbrains.kotlin.ir.util.isFunctionTypeOrSubtype
import org.jetbrains.kotlin.ir.util.isSubtypeOfClass
import org.jetbrains.kotlin.ir.util.parentAsClass
import org.jetbrains.kotlin.ir.util.resolveFakeOverride
import org.jetbrains.kotlin.ir.util.statements
import org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
import org.jetbrains.kotlin.name.CallableId
import org.jetbrains.kotlin.name.ClassId
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.name.Name
import org.jetbrains.kotlin.name.SpecialNames
import java.util.LinkedList
import java.util.UUID

lateinit var messageCollector: MessageCollector

class StateBindingIrTransformer(
    private val pluginContext: IrPluginContext,
    messageCollector: MessageCollector
): IrElementTransformerVoidWithContext() {

    init {
        com.highcapable.hikage.compiler.kcp.transformer.messageCollector = messageCollector
    }

    private val hikageCoreFqName = FqName("com.highcapable.hikage.core")
    private val stateClassId = ClassId.topLevel(FqName("com.highcapable.hikage.core.runtime.State"))
    private val bindCallableId = CallableId(
        packageName = hikageCoreFqName,
        className = FqName("Hikage.PerformerScope"),
        callableName = Name.identifier("bind")
    )
    private val observeCallableId = CallableId(
        packageName = hikageCoreFqName,
        className = FqName("Hikage.PerformerScope"),
        callableName = Name.identifier("observe")
    )
    private val oneTimeCallableId = CallableId(
        packageName = hikageCoreFqName,
        callableName = Name.identifier("oneTime")
    )

    private val stateClass by lazy { pluginContext.referenceClass(stateClassId)!! }
    private val bindFunction by lazy {
        pluginContext.referenceFunctions(bindCallableId).firstOrNull {
            it.owner.extensionReceiverParameter?.type?.isSubtypeOfClass(stateClass) == true
        }!!
    }
    private val observeFunction by lazy {
        pluginContext.referenceFunctions(observeCallableId).firstOrNull {
            it.owner.extensionReceiverParameter?.type?.isSubtypeOfClass(stateClass) == true
        }!!
    }
    private val oneTimeFunction by lazy {
        pluginContext.referenceFunctions(oneTimeCallableId).firstOrNull {
            it.owner.extensionReceiverParameter != null
        }!!
    }

    private val boundVariables = mutableMapOf<IrVariableSymbol, IrValueDeclaration>()
    private val indirectBoundVariables = mutableMapOf<IrVariableSymbol, Pair<IrValueDeclaration, IrExpression?>>()

    override fun visitVariable(declaration: IrVariable): IrStatement {
        val initializer = declaration.initializer
        if ((initializer is IrCall && initializer.symbol.owner.isBindFunction())) {
            declaration.initializer = initializer.extensionReceiver
            declaration.type = initializer.extensionReceiver?.type ?: declaration.type
            boundVariables[declaration.symbol] = declaration
        } else if (initializer is IrGetValue && initializer.symbol in boundVariables) {
            boundVariables[declaration.symbol] = boundVariables[initializer.symbol]!!
        } else {
            boundVariables.entries.firstOrNull { declaration.isDependentOn(it.key) }?.also {
                indirectBoundVariables[declaration.symbol] = it.value to declaration.initializer
            }
        }
        return super.visitVariable(declaration)
    }

    override fun visitCall(expression: IrCall): IrExpression {
        if (expression.symbol.owner.isHikageable()) {
            expression.getHikageViewInitArgument()?.let { initBlock ->
                val movedBoundStatements = mutableMapOf<IrValueDeclaration, MutableList<IrStatement>>()
                val movedIndirectBoundStatements = mutableMapOf<IrValueDeclaration, Pair<IrExpression, MutableList<IrStatement>>>()
                initBlock.function.body?.statements?.forEach {
                    var dependency: IrVariableSymbol? = null
                    it.acceptChildren(
                        object : IrElementVisitorVoid {
                            override fun visitGetValue(expression: IrGetValue) {
                                val symbol = expression.symbol
                                if (symbol is IrVariableSymbol && symbol in boundVariables.keys) {
                                    dependency = symbol
                                }
                                super.visitGetValue(expression)
                            }
                        }, null
                    )
                    if (dependency != null) {
                        movedBoundStatements.getOrPut(dependency.owner) { mutableListOf() }.add(it)
                    }
                }
                val statementsToRemove = movedBoundStatements.values.flatten()
//                expression.setArgumentForParameter(
//                    "init",
                    irLambdaExpression(
                        startOffset = UNDEFINED_OFFSET,
                        endOffset = UNDEFINED_OFFSET,
                        returnType = pluginContext.irBuiltIns.unitType
                    ) { fn ->
                        fn.parent = initBlock.function.parent
                        fn.parameters = initBlock.function.parameters
                        fn.body = DeclarationIrBuilder(pluginContext, fn.symbol).irBlockBody {
                            initBlock.function.body?.statements?.forEach {
                                if (it !in statementsToRemove) {
                                    +it
                                }
                            }
                            movedBoundStatements.keys.forEach { stateValue ->
                                val stateTypeArgument = (stateValue.type as IrSimpleType).arguments[0]
                                +irCall(callee = observeFunction).apply {
                                    arguments.forEachIndexed { index, argument ->
                                        val parameter = observeFunction.owner.parameters[index]
                                        if (parameter.kind == IrParameterKind.ExtensionReceiver) {
                                            putArgument(parameter, irGet(stateValue))
                                        } else if (parameter.kind == IrParameterKind.DispatchReceiver) {
                                            putArgument(parameter, expression.dispatchReceiver!!)
                                        } else if (parameter.type.isFunctionTypeOrSubtype()) {
                                            putArgument(parameter, irLambdaExpression(
                                                startOffset = UNDEFINED_OFFSET,
                                                endOffset = UNDEFINED_OFFSET,
                                                returnType = pluginContext.irBuiltIns.unitType
                                            ) { observeFn ->
                                                observeFn.parent = fn
                                                observeFn.addValueParameter(
                                                    Name.identifier("it_${UUID.randomUUID()}"),
                                                    stateTypeArgument.typeOrFail
                                                )
                                                observeFn.body = DeclarationIrBuilder(pluginContext, observeFn.symbol).irBlockBody {
                                                    val itParameter = observeFn.valueParameters.first()
                                                    movedBoundStatements[stateValue]?.forEach {
                                                        +it.apply {
                                                            transformChildren(
                                                                object : IrElementTransformerVoidWithContext() {
                                                                    override fun visitGetValue(expression: IrGetValue): IrExpression {
                                                                        if (expression.symbol == stateValue) {
                                                                            return irGet(itParameter)
                                                                        }
                                                                        return super.visitGetValue(expression)
                                                                    }
                                                                }, null
                                                            )
                                                        }
                                                    }
                                                }
                                            })
                                        }
                                    }
                                    putTypeArgument(0, stateTypeArgument.typeOrNull)
                                }
                            }
                        }
                    }.also {
                        it.type = initBlock.type
                    }
//                )
            }
        } else if (expression.symbol == oneTimeFunction) {
            (expression.extensionReceiver as? IrGetValue)?.let { extensionReceiver ->
                (extensionReceiver.symbol as? IrVariableSymbol)?.let {
                    boundVariables[it]?.let { variable ->
                        return super.visitGetValue(IrGetValueImpl(
                            startOffset = UNDEFINED_OFFSET,
                            endOffset = UNDEFINED_OFFSET,
                            symbol = variable.symbol
                        ))
                    }
                }
            } ?: error("You must call oneTime function by a StateBinding bound variable")
        }
        return super.visitCall(expression)
    }

    fun irLambdaExpression(
        startOffset: Int,
        endOffset: Int,
        returnType: IrType,
        body: (IrSimpleFunction) -> Unit,
    ): IrExpression {
        val function = pluginContext.irFactory.buildFun {
            this.startOffset = SYNTHETIC_OFFSET
            this.endOffset = SYNTHETIC_OFFSET
            this.returnType = returnType
            origin = IrDeclarationOrigin.LOCAL_FUNCTION_FOR_LAMBDA
            name = SpecialNames.ANONYMOUS
            visibility = DescriptorVisibilities.LOCAL
        }.also(body)

        return IrFunctionExpressionImpl(
            startOffset = startOffset,
            endOffset = endOffset,
            type = pluginContext.irBuiltIns.functionN(function.parameters.size).typeWith(
                function.parameters.map { it.type } + listOf(function.returnType)
            ),
            origin = IrStatementOrigin.LAMBDA,
            function = function
        )
    }

    fun IrSimpleFunction.isBindFunction() = resolveFakeOverride()?.symbol?.equals(bindFunction) == true
}

fun IrExpression.getReferencedVariables(): Set<IrVariableSymbol> {
    val referencedSymbols = mutableSetOf<IrVariableSymbol>()
    this.accept(object : IrElementVisitorVoid {
        override fun visitGetValue(expression: IrGetValue) {
            if (expression.symbol is IrVariableSymbol) {
                referencedSymbols.add(expression.symbol as IrVariableSymbol)
            }
        }

        override fun visitCall(expression: IrCall) {
            val dispatchReceiver = expression.dispatchReceiver
            if (dispatchReceiver is IrGetValue && dispatchReceiver.symbol is IrVariableSymbol) {
                referencedSymbols.add(dispatchReceiver.symbol as IrVariableSymbol)
            }
            val extensionReceiver = expression.arguments.getOrNull(expression.symbol.owner.parameters.indexOfFirst { it.kind == IrParameterKind.ExtensionReceiver })
            if (extensionReceiver is IrGetValue && extensionReceiver.symbol is IrVariableSymbol) {
                referencedSymbols.add(extensionReceiver.symbol as IrVariableSymbol)
            }
            expression.acceptChildren(this, null)
        }

        override fun visitStringConcatenation(expression: IrStringConcatenation) {
            expression.arguments.forEach { arg ->
                if (arg is IrGetValue && arg.symbol is IrVariableSymbol) {
                    referencedSymbols.add(arg.symbol as IrVariableSymbol)
                }
                arg.accept(this, null)
            }
        }

        override fun visitSetValue(expression: IrSetValue) {
            expression.value.accept(this, null)
        }

        override fun visitBlock(expression: IrBlock) {
            expression.statements.forEach { it.accept(this, null) }
        }

        override fun visitWhen(expression: IrWhen) {
            expression.branches.forEach { branch ->
                branch.condition.accept(this, null)
                branch.result.accept(this, null)
            }
        }

        override fun visitLoop(loop: IrLoop) {
            loop.condition.accept(this, null)
            loop.body?.accept(this, null)
        }

        override fun visitBlockBody(body: IrBlockBody) {
            body.statements.forEach { it.accept(this, null) }
        }

        override fun visitVararg(expression: IrVararg) {
            expression.elements.forEach { element ->
                element.accept(this, null)
            }
        }

        override fun visitComposite(expression: IrComposite) {
            expression.statements.forEach { it.accept(this, null) }
        }

        override fun visitElement(element: IrElement) {
            element.acceptChildren(this, null)
        }
    }, null)
    return referencedSymbols
}

fun IrVariable.isDependentOn(targetVariableSymbol: IrVariableSymbol): Boolean {
    val visited = mutableSetOf<IrVariableSymbol>()
    val queue = LinkedList<IrVariableSymbol>()
    queue.add(this.symbol)
    visited.add(this.symbol)

    while (queue.isNotEmpty()) {
        val currentSymbol = queue.removeFirst()

        if (currentSymbol == targetVariableSymbol) {
            return true
        }

        val currentVariable = currentSymbol.owner

        currentVariable.initializer?.let { initializer ->
            initializer.getReferencedVariables().forEach { referencedSymbol ->
                if (referencedSymbol !in visited) {
                    visited.add(referencedSymbol)
                    queue.add(referencedSymbol)
                }
            }
        }
    }
    return false
}

fun IrCall.getArgumentForParameter(parameterName: String): IrExpression? {
    val function = this.symbol.owner

    val valueParameters = function.parameters
    for (i in 0 until valueParameters.size) {
        val parameter = valueParameters[i]

        if ((parameter.kind == IrParameterKind.Regular || parameter.kind == IrParameterKind.Context) && parameter.name.asString() == parameterName) {
            return arguments[i]
        }
    }
    return null
}

fun IrCall.setArgumentForParameter(parameterName: String, expression: IrExpression) {
    val function = this.symbol.owner

    for (i in 0 until function.valueParameters.size) {
        val parameter = function.valueParameters[i]

        if (parameter.name.asString() == parameterName) {
            arguments[i] = expression
        }
    }
}

fun IrAnnotationContainer.isHikageable(): Boolean {
    return hasAnnotation(HikageNames.hikageableClassId)
}

fun IrCall.getHikageViewInitArgument(): IrFunctionExpression? {
    val initArgument = getArgumentForParameter("init") ?: return null

    val argumentType = initArgument.type
    if (initArgument is IrFunctionExpression && argumentType.isFunctionTypeOrSubtype()) {
        return initArgument
    }
    return null
}

object HikageNames {
    val hikageableClassId = ClassId.topLevel(FqName("com.highcapable.hikage.annotation.Hikageable"))
}