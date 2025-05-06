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
 * This file is created by fankes on 2025/5/2.
 */
@file:Suppress("unused")
@file:JvmName("StateUtils")

package com.highcapable.hikage.core.runtime

import com.highcapable.hikage.core.Hikage
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Definition a [Hikage] runtime state interface.
 */
interface State<T> : ReadWriteProperty<Any?, T>

/**
 * Definition a [Hikage] runtime state interface for non-nullable type.
 */
interface NonNullState<T> : State<T> {

    /** The current value of the state. */
    var value: T

    /**
     * Observe the state changes.
     * @param observer the observer to be notified when the state changes.
     */
    fun observe(observer: (T) -> Unit)
}

/**
 * Definition a [Hikage] runtime state interface for nullable type.
 */
interface NullableState<T> : State<T?> {

    /** The current value of the state. */
    var value: T?

    /**
     * Observe the state changes.
     * @param observer the observer to be notified when the state changes.
     */
    fun observe(observer: (T?) -> Unit)
}

/**
 * Implementing the [State] interface mutable state of [Hikage].
 */
class MutableState<T> private constructor() {

    /**
     * The non-nullable state of [Hikage].
     */
    class NonNull<T> internal constructor(private var holder: T) : NonNullState<T> {

        private val observers = mutableSetOf<(T) -> Unit>()

        override var value get() = holder
            set(value) {
                if (holder == value) return
                holder = value
                observers.forEach { it(value) }
            }

        override fun getValue(thisRef: Any?, property: KProperty<*>) = value

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
            this.value = value
        }

        override fun observe(observer: (T) -> Unit) {
            observers += observer
            observer(value)
        }
    }

    /**
     * The nullable state of [Hikage].
     */
    class Nullable<T> internal constructor(private var holder: T?) : NullableState<T?> {

        private val observers = mutableSetOf<(T?) -> Unit>()

        override var value get() = holder
            set(value) {
                if (holder == value) return
                holder = value
                observers.forEach { it(value) }
            }

        override fun getValue(thisRef: Any?, property: KProperty<*>) = value

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
            this.value = value
        }

        override fun observe(observer: (T?) -> Unit) {
            observers += observer
            observer(value)
        }
    }
}

/**
 * Create a mutable state of [Hikage] with the specified value.
 * @param value the initial value of the state.
 * @return [MutableState.NonNull]
 */
fun <T> mutableStateOf(value: T) = MutableState.NonNull(value)

/**
 * Create a mutable state of [Hikage] with the specified value.
 * @param value the initial value of the state.
 * @return [MutableState.Nullable]
 */
fun <T> mutableStateOfNull(value: T? = null) = MutableState.Nullable(value)

/**
 * Set the [Hikage] state value.
 *
 * Usage:
 *
 * ```kotlin
 * val textState = mutableStateOf("Hello World!")
 * var text by textState
 * TextView {
 *     setState(textState) {
 *         text = it
 *     }
 * }
 * // Modify the state.
 * text = "Hello Hikage!"
 * ```
 * @param state the state to be set.
 * @param apply the apply body.
 */
inline fun <T, R> R.setState(state: NonNullState<T>, crossinline apply: R.(T) -> Unit) {
    state.observe {
        this.apply(it)
    }
}

/**
 * Set the [Hikage] state value.
 * @see setState
 * @param state the state to be set.
 * @param apply the apply body.
 */
inline fun <T, R> R.setState(state: NullableState<T>, crossinline apply: R.(T?) -> Unit) {
    state.observe {
        this.apply(it)
    }
}