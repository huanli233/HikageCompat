package com.highcapable.hikage.extension.widget

import android.view.View

inline fun View.ifInEditMode(block: () -> Unit) {
    if (isInEditMode) block()
}