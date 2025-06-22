package com.highcapable.hikage.compiler.kcp.ir

import org.jetbrains.kotlin.ir.declarations.IrClass
import org.jetbrains.kotlin.ir.declarations.IrDeclarationBase
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent
import org.jetbrains.kotlin.ir.declarations.IrFunction
import org.jetbrains.kotlin.ir.declarations.IrPackageFragment
import org.jetbrains.kotlin.ir.util.fqNameWhenAvailable
import org.jetbrains.kotlin.name.FqName

fun IrFunction.isFunctionOfClass(targetClassFqName: FqName): Boolean {
    var currentParent: IrDeclarationParent? = parent

    while (currentParent != null) {
        if (currentParent is IrClass) {
            val classFqName = currentParent.fqNameWhenAvailable
            if (classFqName == targetClassFqName) {
                return true
            }
            currentParent = currentParent.parent
        } else if (currentParent is IrPackageFragment) {
            break
        } else if (currentParent is IrDeclarationBase) {
            currentParent = currentParent.parent
        } else {
            break
        }
    }
    return false
}