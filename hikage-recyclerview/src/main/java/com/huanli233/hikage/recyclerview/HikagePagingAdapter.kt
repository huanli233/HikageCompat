package com.huanli233.hikage.recyclerview

import android.view.ViewGroup
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.highcapable.hikage.core.Hikage
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class HikagePagingAdapterBuilder<T : Any>(
    private val diffCallback: DiffUtil.ItemCallback<T>,
    private val mainDispatcher: CoroutineContext,
    private val workerDispatcher: CoroutineContext
): HikageAbstractBuilder<HikagePagingAdapter<T>, T>() {

    @PublishedApi
    override fun build(): HikagePagingAdapter<T> = object : HikagePagingAdapter<T>(
        diffCallback,
        mainDispatcher,
        workerDispatcher
    ) {
        override fun createView(): Hikage.Delegate<*> =
            this@HikagePagingAdapterBuilder.createView?.invoke(this) ?: error("createView must not be null")

        override fun bindView(hikage: Hikage, item: T) {
            this@HikagePagingAdapterBuilder.bindView?.invoke(this, hikage, item)
        }
    }
}

inline fun <T : Any> hikagePagingAdapter(
    diffCallback: DiffUtil.ItemCallback<T>,
    mainDispatcher: CoroutineContext = Dispatchers.Main,
    workerDispatcher: CoroutineContext = Dispatchers.Default,
    builder: HikagePagingAdapterBuilder<T>.() -> Unit
): HikagePagingAdapter<T> =
    HikagePagingAdapterBuilder<T>(diffCallback, mainDispatcher, workerDispatcher).apply(builder).build()

@Suppress("NOTHING_TO_INLINE")
inline fun <T : Any, VH : RecyclerView.ViewHolder> PagingDataAdapter<T, VH>.withLoadStateHeader(
    headerBuilder: (adapter: PagingDataAdapter<T, VH>) -> LoadStateAdapter<VH>
) = withLoadStateHeader(headerBuilder(this))

@Suppress("NOTHING_TO_INLINE")
inline fun <T : Any, VH : RecyclerView.ViewHolder> PagingDataAdapter<T, VH>.withLoadStateFooter(
    footerBuilder: (adapter: PagingDataAdapter<T, VH>) -> LoadStateAdapter<VH>
) = withLoadStateFooter(footerBuilder(this))

@Suppress("NOTHING_TO_INLINE")
inline fun <T : Any, VH : RecyclerView.ViewHolder> PagingDataAdapter<T, VH>.withLoadStateHeaderAndFooter(
    headerBuilder: (adapter: PagingDataAdapter<T, VH>) -> LoadStateAdapter<VH>,
    footerBuilder: (adapter: PagingDataAdapter<T, VH>) -> LoadStateAdapter<VH>
) = withLoadStateHeaderAndFooter(headerBuilder(this), footerBuilder(this))

abstract class HikagePagingAdapter<T : Any> @JvmOverloads constructor(
    diffCallback: DiffUtil.ItemCallback<T>,
    mainDispatcher: CoroutineContext = Dispatchers.Main,
    workerDispatcher: CoroutineContext = Dispatchers.Default
): PagingDataAdapter<T, HikageViewHolder>(diffCallback, mainDispatcher, workerDispatcher) {

    abstract fun createView(): Hikage.Delegate<*>

    abstract fun bindView(hikage: Hikage, item: T)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HikageViewHolder {
        return HikageViewHolder(createView().create(parent.context, parent, false))
    }

    override fun onBindViewHolder(holder: HikageViewHolder, position: Int) {
        getItem(position)?.let {
            bindView(holder.hikage, it)
        }
    }
}

abstract class HikageLoadStateAdapter : LoadStateAdapter<HikageViewHolder>() {

    abstract fun createView(loadState: LoadState): Hikage.Delegate<*>

    abstract fun bindView(hikage: Hikage, loadState: LoadState)

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): HikageViewHolder {
        return HikageViewHolder(createView(loadState).create(parent.context, parent, false))
    }

    override fun onBindViewHolder(holder: HikageViewHolder, loadState: LoadState) {
        bindView(holder.hikage, loadState)
    }
}

inline val CombinedLoadStates.refreshing
    get() = source.refresh is LoadState.Loading

@Suppress("NOTHING_TO_INLINE")
inline fun CombinedLoadStates.empty(pagingAdapter: PagingDataAdapter<*, *>) =
    refresh is LoadState.NotLoading && pagingAdapter.itemCount == 0

inline val CombinedLoadStates.error
    get() = refresh is LoadState.Error