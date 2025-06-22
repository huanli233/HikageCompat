package com.huanli233.hikage.recyclerview

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.ItemViewDelegate
import com.highcapable.hikage.core.Hikage
import com.huanli233.hikage.recyclerview.HikagePagingAdapterBuilder

class HikageItemBuilder<T>: HikageAbstractBuilder<ItemHikageDelegate<T>, T>() {
    @PublishedApi
    override fun build(): ItemHikageDelegate<T> = object : ItemHikageDelegate<T>() {
        override fun createView(): Hikage.Delegate<*> =
            this@HikageItemBuilder.createView?.invoke(this) ?: error("createView must not be null")

        override fun bindView(hikage: Hikage, item: T) {
            this@HikageItemBuilder.bindView?.invoke(this, hikage, item)
        }
    }
}

inline fun <T> hikageItem(builder: HikageItemBuilder<T>.() -> Unit): ItemHikageDelegate<T> =
    HikageItemBuilder<T>().apply(builder).build()

abstract class ItemHikageDelegate<T>: ItemViewDelegate<T, HikageViewHolder>() {

    abstract fun createView(): Hikage.Delegate<*>

    abstract fun bindView(hikage: Hikage, item: T)

    override fun onCreateViewHolder(context: Context, parent: ViewGroup): HikageViewHolder {
        return HikageViewHolder(createView().create(context, parent, false))
    }

    override fun onBindViewHolder(holder: HikageViewHolder, item: T) =
        bindView(holder.hikage, item)

}

abstract class HikageHolderDelegate<T, VH : HikageViewHolder>: ItemViewDelegate<T, VH>() {

    abstract fun createView(): Hikage.Delegate<*>

    abstract fun createViewHolder(hikage: Hikage): VH

    abstract fun bindView(holder: VH, item: T)

    override fun onCreateViewHolder(context: Context, parent: ViewGroup): VH {
        return createViewHolder(createView().create(context, parent, false))
    }

    override fun onBindViewHolder(holder: VH, item: T) =
        bindView(holder, item)

}

open class HikageViewHolder(
    val hikage: Hikage
): RecyclerView.ViewHolder(hikage.root) {
    /**
     * Get the root view.
     * @return [View]
     */
    val root get() = hikage.root

    /**
     * Get the root view [V].
     * @return [V]
     */
    inline fun <reified V : View> root() = root as? V?

    /**
     * Get the view by [id].
     * @param id the view id.
     * @return [View]
     */
    operator fun get(id: String) = hikage[id]

    /**
     * Get the view by [id].
     * @param id the view id.
     * @return [View] or null.
     */
    fun getOrNull(id: String) = hikage.getOrNull(id)

    /**
     * Get the view by [id] via [V].
     * @param id the view id.
     * @return [V]
     */
    @JvmName("getTyped")
    inline fun <reified V : View> get(id: String) = get(id) as? V

    /**
     * Get the view by [id] via [V].
     * @param id the view id.
     * @return [V] or null.
     */
    @JvmName("getOrNullTyped")
    inline fun <reified V : View> getOrNull(id: String) = getOrNull(id) as? V?
}