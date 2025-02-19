# Hikage

[![GitHub license](https://img.shields.io/github/license/BetterAndroid/Hikage?color=blue)](https://github.com/BetterAndroid/Hikage/blob/main/LICENSE)
[![Telegram](https://img.shields.io/badge/discussion-Telegram-blue.svg?logo=telegram)](https://t.me/BetterAndroid)
[![Telegram](https://img.shields.io/badge/discussion%20dev-Telegram-blue.svg?logo=telegram)](https://t.me/HighCapable_Dev)
[![QQ](https://img.shields.io/badge/discussion%20dev-QQ-blue.svg?logo=tencent-qq&logoColor=red)](https://qm.qq.com/cgi-bin/qm/qr?k=Pnsc5RY6N2mBKFjOLPiYldbAbprAU3V7&jump_from=webapi&authKey=X5EsOVzLXt1dRunge8ryTxDRrh9/IiW1Pua75eDLh9RE3KXE+bwXIYF5cWri/9lf)

<img src="img-src/icon.png" width = "100" height = "100" alt="LOGO"/>

An Android responsive UI building tool.

English | [简体中文](README-zh-CN.md)

| <img src="https://github.com/BetterAndroid/.github/blob/main/img-src/logo.png?raw=true" width = "30" height = "30" alt="LOGO"/> | [BetterAndroid](https://github.com/BetterAndroid) |
|---------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------|

This project belongs to the above-mentioned organization, **click the link above to follow this organization** and discover more good projects.

## What's this

This is an Android responsive UI build tool designed to focus on **Real-time code building UI**.

The name is taken from the original song "Haru**hikage**" in "BanG Dream It's MyGO!!!!!".

<details><summary>Why...</summary>
  <div align="center">
  <img src="https://i0.hdslb.com/bfs/garb/item/fa1ffd8af57626ca4f6bd562bac097239d36838b.png" width = "100" height = "100" alt="LOGO"/>

  **なんで春日影レイアウト使いの？**
  </div>
</details>

Unlike Jetpack Compose's declarative UI, Hikage focuses on Android native platforms,
and its design goal is to enable developers to quickly build UIs and directly support Android native components.

Hikage is just a UI build tool and does not provide any UI components themselves.

Rejecting duplicate wheels, our solution is always compatible and efficient. Now you can abandon ViewBinding and XML and even `findViewById` and try
to use the code layout directly.

The properties in Android view will be automatically generated with the Gradle plugin, and you can use it like in XML.

It does not need to consider how to complete complex attribute settings in the code, especially some third-party libraries do not provide dynamic
modifications to their custom views.

## Effects

> Original layout

```xml
<LinearLayout
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:padding="16dp">

    <TextView
        android:id="@+id/text_view"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Hello, World!"
        android:textSize="16sp"
        android:textColor="#000000"
        android:layout_marginTop="16dp"
        android:layout_marginStart="16dp"
        android:layout_marginEnd="16dp"
        android:layout_marginBottom="16dp"
        android:gravity="center" />

    <com.google.android.material.button.MaterialButton
        android:id="@+id/button"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Click Me!"
        android:textSize="16sp"
        android:textColor="#FFFFFF"
        android:backgroundTint="#FF0000"
        android:layout_marginTop="16dp"
        android:layout_marginStart="16dp"
        android:layout_marginEnd="16dp"
        android:layout_marginBottom="16dp"
        android:gravity="center" />
</LinearLayout>
```

> Using Hikage

```kotlin
// Using Hikage to build a layout requires a UI Context.
val context: Context
// Make sure the Context is UI Context.
if (!context.isUiContext) return
// Start building the layout, be careful to make sure the context parameter is initialized.
// According to the Android native component features,
// the attributes (`attrs`) after layout construction will be fixed and cannot be modified dynamically.
val hikage = Hikageable(
    context = context,
    // You can also customize the actions after each view is created.
    onViewCreated = { name, view ->
        // ...
    }
) {
    LinearLayout(
        attrs = {
            android.layout_width = MATCH_PARENT
            android.layout_height = MATCH_PARENT
            android.orientation = VERTICAL
            android.padding = 16.dp
        },
        // You can manually specify layout parameters.
        lpparams = {
            gravity = Gravity.CENTER
        }
    ) {
        TextView(
            // Set the ID using string form, you can use large camel, small camel,
            // or underscore form, which will be converted to small camel form when generated.
            id = "text_view",
            // You can set properties directly using attrs without considering who they belong to.
            attrs = {
                android.layout_width = WRAP_CONTENT
                android.layout_height = WRAP_CONTENT
                android.text = "Hello, World!"
                android.textSize = 16.sp
                android.textColor = Color.BLACK
                android.layout_marginTop = 16.dp
                android.layout_marginStart = 16.dp
                android.layout_marginEnd = 16.dp
                android.layout_marginBottom = 16.dp
                android.gravity = Gravity.CENTER
                // Or use string form to set properties (note that there is no spelling check).
                namespace("android") {
                    set("id", R.id.text_view)
                    set("layout_margin", 16.dp)
                    set("layout_gravity", Gravity.CENTER)
                    // ...
                }
            },
            // You can also manually specify layout parameters.
            lpparams = {
                gravity = Gravity.CENTER
            },
            // Perform initialization operations.
            // You can also manually set properties.
            initialize = {
                text = "Hello, World!"
                textSize = 16f
                setTextColor(Color.BLACK)
                // Or more operations.
                doOnLayout {
                    // ...
                }
            }
        )
        // Use third-party views.
        View<MaterialButton>(
            id = "button",
            attrs = {
                android.layout_width = WRAP_CONTENT
                android.layout_height = WRAP_CONTENT
                android.text = "Click Me!"
                android.textSize = 16.sp
                android.textColor = Color.WHITE
                android.backgroundTint = Color.RED
                android.layout_marginTop = 16.dp
                android.layout_marginStart = 16.dp
                android.layout_marginEnd = 16.dp
                android.layout_marginBottom = 16.dp
                android.gravity = Gravity.CENTER
            }
        )
    }
}
// Get the root layout.
val root = hikage.root
// You can also convert it to the type of the first layout, equivalent to hikage.root as LinearLayout.
// Thanks to Kotlin's features, using Hikageable(...) { ... }.rootAsType() directly does not require filling in generics.
val root = hikage.rootAsType<LinearLayout>()
// Set to Activity.
setContentView(root)
// Get the built layout internal components (first solution).
val textView = hikage.textView
val button = hikage.button
// Get the built layout internal components (second solution).
val textView = hikage.get<TextView>("text_view")
val button = hikage.get<MaterialButton>("button")
```

## Preview with Android Studio

Unlike XML, Hikage does not support live previews, but you can inherit the `HikagePreview` provided by Hikage,
and pass in your layout, and then view the preview in the pane on the right of Android Studio.

You can also use `isInEditMode` in your code to avoid displaying actual logical code that cannot be displayed in preview mode.

```kotlin
class MyPreview(context: Context, attrs: AttributeSet?) : HikagePreview(context, attrs) {

    override fun onPreview(): Hikage {
        // Return to your layout.
        return Hikageable {
            Button(
                attrs = {
                    android.layout_width = WRAP_CONTENT
                    android.layout_height = WRAP_CONTENT
                    android.text = "Click Me!"
                }
            )
        }
    }
}
```

Note `HikagePreview` is for preview only and should not be used in actual code, otherwise an exception will be thrown.

Hikage may have plans to support Java, but Kotlin is still recommended.

## WIP

This project is still a work in progress. If you have any suggestions or feedback, feel free to open an issue or a pull request.

## Promotion

<!--suppress HtmlDeprecatedAttribute -->
<div align="center">
     <h2>Hey, please stay! 👋</h2>
     <h3>Here are related projects such as Android development tools, UI design, Gradle plugins, Xposed Modules and practical software. </h3>
     <h3>If the project below can help you, please give me a star! </h3>
     <h3>All projects are free, open source, and follow the corresponding open source license agreement. </h3>
     <h1><a href="https://github.com/fankes/fankes/blob/main/project-promote/README.md">→ To see more about my projects, please click here ←</a></h1>
</div>

## Star History

![Star History Chart](https://api.star-history.com/svg?repos=BetterAndroid/Hikage&type=Date)

## License

- [Apache-2.0](https://www.apache.org/licenses/LICENSE-2.0)

```
Apache License Version 2.0

Copyright (C) 2019 HighCapable

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

Copyright © 2019 HighCapable