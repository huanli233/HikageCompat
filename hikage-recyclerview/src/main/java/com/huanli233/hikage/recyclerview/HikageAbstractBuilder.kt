package com.huanli233.hikage.recyclerview

import com.highcapable.hikage.core.Hikage

abstract class HikageAbstractBuilder<R, T> {
    internal var createView: (R.() -> Hikage.Delegate<*>)? = null
    internal var bindView: (R.(Hikage, T) -> Unit)? = null
    fun createView(block: R.() -> Hikage.Delegate<*>) {
        createView = block
    }
    fun bindView(block: R.(Hikage, T) -> Unit) {
        bindView = block
    }
    @PublishedApi internal abstract fun build(): R
}