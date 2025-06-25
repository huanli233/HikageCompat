# 展望未来

> 未来是美好的，也是不确定的，让我们共同期待 `HikageCompat` 在未来的发展空间。

## 未来的计划

> 这里收录了 `HikageCompat` 可能会在后期添加的功能。

### StateBinding

这是对于 Hikage 的状态机制的增强，旨在提供类似于 `DataBinding` 的功能。

> 示例如下

```kotlin
setContentView {
    // 调用 StateBinding 的 bind 方法，得到一个原始 UiState 对象
    val uiState = viewModel.uiState.collectAsHikageState().bind()
    val helloText = "Hello, ${uiState.name}!"
    
    TextView {
        // 在编译时，下面的代码将被 hikage-compiler 使用 observe 调用包装。
        text = helloText
    }
}
```

### 处理 AttrtibuteSet

`Hikage` 未来将会尝试支持处理 `AttributeSet` 来对接 XML 原始的属性以实现接管一些并未在代码中对布局属性开放自定义的第三方组件。

不过，由于 Android 的 `attrs` 仅支持 Compiled XML ，且构建过程中资源编译在代码编译之前，因此难以实现，后续会考虑是否尝试编写。

> 示例如下

```kotlin
TextView(
    id = "text_view",
    // 通过 AttributeSet 传入的属性
    attrs = {
        namespace("android") {
            set("text", "Hello, World!")
            set("textSize", "16sp")
            set("gravity", "center")
        }
    }
) {
    // 通过代码传入的属性
    text = "Hello, World!"
    textSize = 16f
    gravity = Gravity.CENTER
}
```