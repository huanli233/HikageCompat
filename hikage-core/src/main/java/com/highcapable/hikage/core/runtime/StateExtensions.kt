package com.highcapable.hikage.core.runtime

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * Observe the state changes with lifecycle awareness.
 * The [observer] will only receive updates when the [lifecycleOwner] is at least in the [minActiveState].
 * @param lifecycleOwner the [LifecycleOwner] to observe with.
 * @param minActiveState the minimum active state for the observer to receive updates. Default is [Lifecycle.State.STARTED].
 * @param observer the observer to be notified when the state changes.
 */
inline fun <T> NonNullState<T>.observe(
    lifecycleOwner: LifecycleOwner,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    crossinline observer: (T) -> Unit
) {
    lifecycleOwner.lifecycleScope.launch {
        lifecycleOwner.repeatOnLifecycle(minActiveState) {
            asStateFlow.collect {
                observer(it)
            }
        }
    }
}

/**
 * Observe the state changes with lifecycle awareness.
 * The [observer] will only receive updates when the [lifecycleOwner] is at least in the [minActiveState].
 * @param lifecycleOwner the [LifecycleOwner] to observe with.
 * @param minActiveState the minimum active state for the observer to receive updates. Default is [Lifecycle.State.STARTED].
 * @param observer the observer to be notified when the state changes.
 */
inline fun <T> NullableState<T>.observe(
    lifecycleOwner: LifecycleOwner,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    crossinline observer: (T?) -> Unit
) {
    lifecycleOwner.lifecycleScope.launch {
        lifecycleOwner.repeatOnLifecycle(minActiveState) {
            asStateFlow.collect {
                observer(it)
            }
        }
    }
}

fun <T> StateFlow<T>.collectAsHikageState(lifecycleOwner: LifecycleOwner): NonNullState<T> {
    val hikageState = mutableStateOf(this.value)
    lifecycleOwner.lifecycleScope.launch {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            this@collectAsHikageState.collect {
                hikageState.value = it
            }
        }
    }
    return hikageState
}

fun <T> Flow<T>.collectAsHikageState(lifecycleOwner: LifecycleOwner): NullableState<T?> {
    val hikageState = mutableStateOfNull<T>()
    lifecycleOwner.lifecycleScope.launch {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            this@collectAsHikageState.collect {
                hikageState.value = it
            }
        }
    }
    return hikageState
}

fun <T> Flow<T>.collectAsHikageState(
    lifecycleOwner: LifecycleOwner,
    initialValue: T
): NonNullState<T> {
    val hikageState = mutableStateOf(initialValue)
    lifecycleOwner.lifecycleScope.launch {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            this@collectAsHikageState.collect {
                hikageState.value = it
            }
        }
    }
    return hikageState
}

fun <T> LiveData<T>.toHikageState(lifecycleOwner: LifecycleOwner): NonNullState<T> {
    val initialValue = this.value ?: throw IllegalArgumentException("LiveData must have an initial value for NonNullState conversion")
    val hikageState = mutableStateOf<T>(initialValue)

    lifecycleOwner.lifecycle.addObserver(object : LifecycleEventObserver {
        private var liveDataObserver: Observer<T>? = null

        override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
            if (event == Lifecycle.Event.ON_START) {
                liveDataObserver = Observer { value ->
                    if (value != null) {
                        hikageState.value = value
                    }
                }
                this@toHikageState.observe(source, liveDataObserver!!)
            } else if (event == Lifecycle.Event.ON_STOP) {
                liveDataObserver?.let { this@toHikageState.removeObserver(it) }
                liveDataObserver = null
            }
        }
    })
    return hikageState
}

fun <T> LiveData<T?>.toHikageNullableState(lifecycleOwner: LifecycleOwner): NullableState<T?> {
    val hikageState = mutableStateOfNull<T>(this.value, lifecycleOwner)

    lifecycleOwner.lifecycle.addObserver(object : LifecycleEventObserver {
        private var liveDataObserver: Observer<T?>? = null

        override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
            if (event == Lifecycle.Event.ON_START) {
                liveDataObserver = Observer { value ->
                    hikageState.value = value
                }
                this@toHikageNullableState.observe(source, liveDataObserver!!)
            } else if (event == Lifecycle.Event.ON_STOP) {
                liveDataObserver?.let { this@toHikageNullableState.removeObserver(it) }
                liveDataObserver = null
            }
        }
    })
    return hikageState
}