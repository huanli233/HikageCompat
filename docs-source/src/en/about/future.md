# Looking for Future

> The future is bright and uncertain, let us look forward to the future development space of `HikageCompat`.

## Future Plans

> Features that `HikageCompat` may add later are included here.

### StateBinding

This is an enhancement to Hikage's State, aiming to provide functionality similar to `DataBinding`.

> The following example

```kotlin
setContentView {
    // Call the bind method of StateBinding to obtain a raw UiState object
    val uiState = viewModel.uiState.collectAsHikageState().bind()
    val helloText = "Hello, ${uiState.name}!"

    TextView {
        // At compile time, the code below will be wrapped with an observe call by hikage-compiler.
        text = helloText
    }
}
```

### Process AttrtibuteSet

In the future, `Hikage` will try to support processing `AttributeSet` to connect to the original attributes of XML to take over some third-party components that do not open customization of layout attributes in the code.

However, since Android's `attrs` only supports Compiled XML, and resource compilation occurs before code compilation during the build process, it is difficult to implement. We will consider whether to try to write it later.

> The following example

```kotlin
TextView(
    id = "text_view",
    // Attributes passed through AttributeSet.
    attrs = {
        namespace("android") {
            set("text", "Hello, World!")
            set("textSize", "16sp")
            set("gravity", "center")
        }
    }
) {
    // Attributes passed through code.
    text = "Hello, World!"
    textSize = 16f
    gravity = Gravity.CENTER
}
```