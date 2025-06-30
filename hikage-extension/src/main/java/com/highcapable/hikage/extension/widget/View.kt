package com.highcapable.hikage.extension.widget

import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.view.ViewCompat

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

fun View.setBackgroundCompat(background: Drawable?) {
    ViewCompat.setBackground(this, background)
}