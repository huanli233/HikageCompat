package com.highcapable.hikage.extension.widget

import android.widget.TextView
import androidx.annotation.StringRes

var TextView.textRes: Int
    get() = throw UnsupportedOperationException()
    set(@StringRes value) = setText(value)