# Looking for Future

> The future is bright and uncertain, let us look forward to the future development space of `HikageCompat`.

## Future Plans

> Features that `HikageCompat` may add later are included here.

### Generate Components ID

`Hikage` may support the direct call function to generate component IDs customized with strings as required in the future.

> The following example

```kotlin
object MyLayout : HikageBuilder {

    override fun build() = Hikageable(context) {
        LinearLayout(
            id = "lin_layout",
            lparams = LayoutParams(matchParent = true),
            init = {
                orientation = LinearLayout.VERTICAL
                gravity = Gravity.CENTER
            }
        ) {
            TextView(id = "text_view") {
                text = "Hello, World!"
                textSize = 16f
                gravity = Gravity.CENTER
            }
        }
    }
}

val context: Context
// Create TypedHikage.
val myLayout = MyLayout.asTyped().build().create(context)
// Or, use lazy init.
val myLayout by context.lazyTypedHikage(MyLayout)
// Directly call the ID generated from the string.
val linLayout = myLayout.linLayout
val textView = myLayout.textView
// Get the root layout, i.e. LinearLayout.
val root = myLayout.root
```

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