package com.highcapable.hikage.extension.widget

import android.graphics.Typeface
import android.widget.TextView
import androidx.annotation.StringRes

var TextView.textRes: Int
    get() = throw UnsupportedOperationException()
    set(@StringRes value) = setText(value)

var TextView.editModeText: CharSequence
    get() = text
    set(value) {
        if (isInEditMode) text = value
    }

fun TextView.boldTypeFace() {
    typeface = Typeface.defaultFromStyle(Typeface.BOLD)
}

fun TextView.italicTypeFace() {
    typeface = Typeface.defaultFromStyle(Typeface.ITALIC)
}

fun TextView.boldItalicTypeFace() {
    typeface = Typeface.defaultFromStyle(Typeface.BOLD_ITALIC)
}

fun TextView.normalTypeFace() {
    typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
}