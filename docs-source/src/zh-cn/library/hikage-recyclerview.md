# hikage-recyclerview

![Maven Central](https://img.shields.io/maven-central/v/com.huanli233.hikage.compat/hikage-recyclerview?logo=apachemaven&logoColor=orange)
<span style="margin-left: 5px"/>
![Android Min SDK](https://img.shields.io/badge/Min%20SDK-14-orange?logo=android)

这是 Hikage 针对 RecyclerView 相关功能的扩展依赖，核心基于 [MultiType](https://github.com/drakeet/MultiType) 与 [Paging](https://developer.android.google.cn/topic/libraries/architecture/paging/v3-overview)。

## 配置依赖

你可以使用如下方式将此模块添加到你的项目中。

### SweetDependency (推荐)

在你的项目 `SweetDependency` 配置文件中添加依赖。

```yaml
libraries:
  com.highcapable.hikage:
    hikage-recyclerview:
      version: +
```

在你的项目 `build.gradle.kts` 中配置依赖。

```kotlin
implementation(com.highcapable.hikage.hikage.recyclerview)
```

### 传统方式

在你的项目 `build.gradle.kts` 中配置依赖。

```kotlin
implementation("com.highcapable.hikage:hikage-recyclerview:<version>")
```

请将 `<version>` 修改为此文档顶部显示的版本。

## 功能介绍

你可以 [点击这里](kdoc://hikage-recyclerview) 查看 KDoc。

### MultiType

Hikage 为 `MultiType` 的 `Delegate` 提供了封装。

> 示例如下

```kotlin
class MenuItemDelegate: ItemHikageDelegate<MenuItem>() {

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

对于 `Paging` ，Hikage 提供了 `PagingDataAdapter` 和 `LoadStateAdapter` 的封装。

> PagingDataAdapter 示例如下

```kotlin
class VideoPagingAdapter: HikagePagingAdapter<VideoInfo>(VideoInfoDiffCallback()) {

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

> LoadStateAdapter 示例如下

```kotlin
class LoadStateAdapter(
    private val retry: (() -> Unit)? = null
): HikageLoadStateAdapter() {

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