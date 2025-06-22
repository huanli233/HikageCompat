# hikage-extension

![Maven Central](https://img.shields.io/maven-central/v/com.huanli233.hikage.compat/hikage-recyclerview?logo=apachemaven&logoColor=orange)
<span style="margin-left: 5px"/>
![Android Min SDK](https://img.shields.io/badge/Min%20SDK-14-orange?logo=android)

This is the Hikage extension dependency for RecyclerView-related features. It is primarily based on [MultiType](https://github.com/drakeet/MultiType) and [Paging](https://developer.android.google.cn/topic/libraries/architecture/paging/v3-overview).

## Configure Dependency

You can add this module to your project using one of the following methods.

### SweetDependency (Recommended)

Add dependency in your project's `SweetDependency` configuration file.

```yaml
libraries:
  com.highcapable.hikage:
    hikage-recyclerview:
      version: +
````

Configure dependency in your project `build.gradle.kts`.

```kotlin
implementation(com.highcapable.hikage.hikage.recyclerview)
```

### Traditional Method

Configure dependency in your project `build.gradle.kts`.

```kotlin
implementation("com.highcapable.hikage:hikage-recyclerview:<version>")
```

Please change `<version>` to the version displayed at the top of this document.

## Features

You can view the KDoc [click here](kdoc://hikage-extension-betterandroid).

### MultiType

Hikage provides wrappers for `Delegate` in `MultiType`.

> Example:

```kotlin
class MenuItemDelegate : ItemHikageDelegate<MenuItem>() {

    override fun createView(): Hikage.Delegate<*> = Hikageable<ViewGroup.MarginLayoutParams> {
        MaterialButton(
            id = "menuBtn",
            lparams = widthMatchParent {
                updateMargins(horizontal = 12.dp, vertical = 2.dp)
            }
        )
    }

    override fun bindView(hikage: Hikage, item: MenuItem) {
        hikage.get<MaterialButton>("menuBtn").apply {
            text = context.getString(item.title)
            icon = ContextCompat.getDrawable(context, item.icon)
        }
    }
}
```

### Paging

For `Paging`, Hikage provides wrappers for `PagingDataAdapter` and `LoadStateAdapter`.

> Example for PagingDataAdapter:

```kotlin
class VideoPagingAdapter : HikagePagingAdapter<VideoInfo>(VideoInfoDiffCallback()) {

    override fun createView(): Hikage.Delegate<*> = Hikageable {
        VideoCard(id = "card", lparams = widthMatchParent())
    }

    override fun bindView(hikage: Hikage, item: VideoInfo) {
        hikage.get<VideoCard>("card").apply {
            setVideoTitle(item.title)
            setVideoCover(item.pic)
            setViews(item.stat.view.formatNumber())
            setUploader(item.owner.name)
            binding.root.setOnClickListener {
                context.start<VideoInfoActivity> {
                    putExtra("bvid", item.bvid)
                }
            }
        }
    }
}
```

> Example for LoadStateAdapter:

```kotlin
class LoadStateAdapter(
    private val retry: (() -> Unit)? = null
) : HikageLoadStateAdapter() {

    override fun createView(loadState: LoadState): Hikage.Delegate<*> = Hikageable {
        // layout...
    }

    override fun bindView(hikage: Hikage, loadState: LoadState) {
        if (loadState is LoadState.Error) {
            hikage.get<TextView>("load_state_error_msg").text = loadState.error.localizedMessage
        }
        hikage.get<ProgressBar>("load_state_progress_indicator").isVisible = loadState is LoadState.Loading
        hikage.get<Button>("load_state_retry_btn").isVisible = loadState is LoadState.Error && retry != null
        hikage.get<TextView>("load_state_error_msg").isVisible = loadState is LoadState.Error
    }
}
```
