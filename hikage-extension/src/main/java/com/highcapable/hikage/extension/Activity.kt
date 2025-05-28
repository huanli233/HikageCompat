/*
 * Hikage - An Android responsive UI building tool.
 * Copyright (C) 2019 HighCapable
 * https://github.com/BetterAndroid/Hikage
 *
 * Apache License Version 2.0
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * This file is created by fankes on 2025/2/27.
 */
@file:Suppress("unused")
@file:JvmName("ActivityUtils")

package com.highcapable.hikage.extension

import android.app.Activity
import android.widget.FrameLayout
import androidx.activity.ComponentActivity
import androidx.lifecycle.LifecycleOwner
import com.highcapable.hikage.core.Hikage
import com.highcapable.hikage.core.base.HikageFactoryBuilder
import com.highcapable.hikage.core.base.HikagePerformer
import com.highcapable.hikage.core.base.Hikageable

/**
 * @see Activity.setContentView
 * @see Hikageable
 * @return [Hikage]
 */
inline fun Activity.setContentView(
    factory: HikageFactoryBuilder.() -> Unit = {},
    performer: HikagePerformer<FrameLayout.LayoutParams>
) = if (this is LifecycleOwner) {
    Hikageable(context = this, lifecycleOwner = this, factory = factory, performer = performer).apply { setContentView(root) }
} else {
    Hikageable(context = this, factory = factory, performer = performer).apply { setContentView(root) }
}

/**
 * @see Activity.setContentView
 * @see Hikage
 */
fun Activity.setContentView(hikage: Hikage) = setContentView(hikage.root)

/**
 * @see Activity.setContentView
 * @see Hikage.Delegate
 * @return [Hikage]
 */
fun Activity.setContentView(delegate: Hikage.Delegate<*>) =
    if (this is LifecycleOwner) delegate.create(context = this, lifecycleOwner = this).apply { setContentView(root) }
    else delegate.create(context = this).apply { setContentView(root) }

inline val ComponentActivity.lifecycleOwner
    get() = this