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

import androidx.lifecycle.LifecycleOwner
import com.highcapable.hikage.core.Hikage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Definition a [Hikage] runtime state interface.
 */
interface State<T> : ReadWriteProperty<Any?, T> {
    val asStateFlow: StateFlow<T>
}

/**
 * Definition a [Hikage] runtime state interface for non-nullable type.
 */
interface NonNullState<T> : State<T> {

    /** The current value of the state. */
    var value: T

}

/**
 * Definition a [Hikage] runtime state interface for nullable type.
 */
interface NullableState<T> : State<T?> {

    /** The current value of the state. */
    var value: T?

}

/**
 * Implementing the [State] interface mutable state of [Hikage].
 */
class MutableState<T> private constructor() {

    /**
     * The non-nullable state of [Hikage].
     */
    class NonNull<T> internal constructor(initialValue: T, ) : NonNullState<T> {

        private val observers = mutableSetOf<(T) -> Unit>()
        private val _stateFlow = MutableStateFlow(initialValue)
        override val asStateFlow: StateFlow<T> = _stateFlow

        override var value: T
            get() = _stateFlow.value
            set(value) {
                _stateFlow.value = value
            }

        override fun getValue(thisRef: Any?, property: KProperty<*>) = value
        override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
            this.value = value
        }
    }

    /**
     * The nullable state of [Hikage].
     */
    class Nullable<T> internal constructor(initialValue: T?) : NullableState<T?> {

        private val _stateFlow = MutableStateFlow(initialValue)
        override val asStateFlow: StateFlow<T?> = _stateFlow

        override var value: T?
            get() = _stateFlow.value
            set(value) {
                _stateFlow.value = value
            }

        override fun getValue(thisRef: Any?, property: KProperty<*>) = value
        override fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
            this.value = value
        }
    }
}

/**
 * Create a mutable state of [Hikage] with the specified value.
 * @param value the initial value of the state.
 * @return [MutableState.NonNull]
 */
fun <T> mutableStateOf(
    value: T
) = MutableState.NonNull(value)

/**
 * Create a mutable state of [Hikage] with the specified value.
 * @param value the initial value of the state.
 * @return [MutableState.Nullable]
 */
fun <T> mutableStateOfNull(
    value: T? = null,
    lifecycleOwner: LifecycleOwner? = null
) = MutableState.Nullable(value)
