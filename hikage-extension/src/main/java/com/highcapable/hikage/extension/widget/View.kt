package com.highcapable.hikage.extension.widget

import android.view.View

inline fun View.ifInEditMode(block: () -> Unit) {
    if (isInEditMode) block()
}

inline fun View.onClick(crossinline block: () -> Unit) {
    setOnClickListener { block() }
}

inline fun View.onLongClick(crossinline block: () -> Any) {
    setOnLongClickListener {
        block() as? Boolean == true
    }
}

