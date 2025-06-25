package com.huanli233.hikage.recyclerview

import android.annotation.SuppressLint
import androidx.annotation.CheckResult
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.ItemViewBinder
import com.drakeet.multitype.ItemViewDelegate
import com.drakeet.multitype.MultiTypeAdapter
import com.drakeet.multitype.OneToManyFlow
import com.drakeet.multitype.Types
import kotlin.reflect.KClass

class MultiTypeRegister(val adapter: MultiTypeAdapter) {
    inline operator fun <reified T : Any> ItemViewDelegate<T, *>.unaryPlus() {
        this@MultiTypeRegister.adapter.register(this)
    }
    inline operator fun <reified T : Any> ItemViewBinder<T, *>.unaryPlus() {
        this@MultiTypeRegister.adapter.register(this)
    }
    fun <T> register(clazz: Class<T>, delegate: ItemViewDelegate<T, *>) = adapter.register(clazz, delegate)

    inline fun <reified T : Any> register(delegate: ItemViewDelegate<T, *>) = adapter.register(delegate)

    inline fun <reified T : Any> register(
        // Keep this parameter to provide the explicit relationship
        @Suppress("UNUSED_PARAMETER") clazz: KClass<T>,
        delegate: ItemViewDelegate<T, *>,
    ) = adapter.register(clazz, delegate)

    fun <T> register(clazz: Class<T>, binder: ItemViewBinder<T, *>) = adapter.register(clazz, binder)

    inline fun <reified T : Any> register(binder: ItemViewBinder<T, *>) = adapter.register(binder)

    inline fun <reified T : Any> register(clazz: KClass<T>, binder: ItemViewBinder<T, *>) = adapter.register(clazz, binder)

    /**
     * Registers a type class to multiple item view delegates. If you have registered the
     * class, it will override the original delegate(s). Note that the method is non-thread-safe
     * so that you should not use it in concurrent operation.
     *
     * Note that the method should not be called after
     * [RecyclerView.setAdapter], or you have to call the setAdapter again.
     *
     * @param clazz the class of a item
     * @param <T> the item data type
     * @return [OneToManyFlow] for setting the delegates
     * @see [register]
     */
    @CheckResult
    fun <T> register(clazz: Class<T>): OneToManyFlow<T> = adapter.register(clazz)

    @CheckResult
    fun <T : Any> register(clazz: KClass<T>): OneToManyFlow<T> {
        return register(clazz.java)
    }

    /**
     * Registers all of the contents in the specified [Types]. If you have registered a
     * class, it will override the original delegate(s). Note that the method is non-thread-safe
     * so that you should not use it in concurrent operation.
     *
     * Note that the method should not be called after
     * [RecyclerView.setAdapter], or you have to call the setAdapter
     * again.
     *
     * @param types a [Types] containing contents to be added to this adapter inner [Types]
     * @see [register]
     * @see [register]
     */
    fun registerAll(types: Types) = adapter.registerAll(types)
}

inline fun RecyclerView.initMultiType(
    builder: MultiTypeRegister.() -> Unit
): MultiTypeAdapter = MultiTypeAdapter().apply {
    MultiTypeRegister(this).builder()
    this@initMultiType.adapter = this
}

inline val RecyclerView.multiTypeAdapter: MultiTypeAdapter get() = adapter as MultiTypeAdapter

inline fun MultiTypeAdapter.register(
    builder: MultiTypeRegister.() -> Unit
): MultiTypeAdapter = apply {
    MultiTypeRegister(this).builder()
}

@SuppressLint("NotifyDataSetChanged")
fun MultiTypeAdapter.setData(data: List<Any>) {
    items = data
    notifyDataSetChanged()
}