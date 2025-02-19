# Hikage

[![GitHub license](https://img.shields.io/github/license/BetterAndroid/Hikage?color=blue)](https://github.com/BetterAndroid/Hikage/blob/main/LICENSE)
[![Telegram](https://img.shields.io/badge/discussion-Telegram-blue.svg?logo=telegram)](https://t.me/BetterAndroid)
[![Telegram](https://img.shields.io/badge/discussion%20dev-Telegram-blue.svg?logo=telegram)](https://t.me/HighCapable_Dev)
[![QQ](https://img.shields.io/badge/discussion%20dev-QQ-blue.svg?logo=tencent-qq&logoColor=red)](https://qm.qq.com/cgi-bin/qm/qr?k=Pnsc5RY6N2mBKFjOLPiYldbAbprAU3V7&jump_from=webapi&authKey=X5EsOVzLXt1dRunge8ryTxDRrh9/IiW1Pua75eDLh9RE3KXE+bwXIYF5cWri/9lf)

<img src="img-src/icon.png" width = "100" height = "100" alt="LOGO"/>

一个 Android 响应式 UI 构建工具。

[English](README.md) | 简体中文

| <img src="https://github.com/BetterAndroid/.github/blob/main/img-src/logo.png?raw=true" width = "30" height = "30" alt="LOGO"/> | [BetterAndroid](https://github.com/BetterAndroid) |
|---------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------|

这个项目属于上述组织，**点击上方链接关注这个组织**，发现更多好项目。

## 这是什么

这是一个 Android 响应式 UI 构建工具，它的设计聚焦于 **实时代码构建 UI**。

名称取自 「BanG Dream It's MyGO!!!!!」 中的原创歌曲《春日影》(Haru**hikage**)。

<details><summary>为什么要...</summary>
  <div align="center">
  <img src="https://i0.hdslb.com/bfs/garb/item/fa1ffd8af57626ca4f6bd562bac097239d36838b.png" width = "100" height = "100" alt="LOGO"/>

  **なんで春日影レイアウト使いの？**
  </div>
</details>

不同于 Jetpack Compose 的声明式 UI，Hikage 专注于 Android 原生平台，它的设计目标是为了让开发者能够快速构建 UI 并可直接支持 Android 原生组件。

Hikage 只是一个 UI 构建工具，自身并不提供任何 UI 组件。

拒绝重复造轮子，我们的方案始终是兼容与高效，现在你可以抛弃 ViewBinding 和 XML 甚至是 `findViewById`，直接来尝试使用代码布局吧。

Android View 中的属性将配合 Gradle 插件实现自动生成，你可以像在 XML 一样去使用它，
而不需要考虑在代码中如何完成复杂的属性设置，特别是一些第三方库并未对它们的自定义 View 提供代码中的属性动态修改。

## 效果展示

> 原始布局

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

> 使用 Hikage

```kotlin
// 使用 Hikage 构建布局，需要有一个 UI Context
val context: Context
// 确保 Context 为 UI Context
if (!context.isUiContext) return
// 开始构建布局，请注意确保 context 参数已初始化
// 根据 Android 原生组件特性，布局构建后属性 (`attrs`) 将固定，无法动态修改
val hikage = Hikageable(
    context = context,
    // 你还可以自定义每个 View 被创建后的操作
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
        // 你可以手动指定布局参数
        lpparams = {
            gravity = Gravity.CENTER
        }
    ) {
        TextView(
            // 使用字符串形式设置 ID，可以使用大驼峰、小驼峰或下划线形式，在生成时将被转换为小驼峰形式
            id = "text_view",
            // 你可以直接使用 attrs 来设置属性，无需考虑它们属于谁
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
                // 或者使用字符串形式设置属性 (注意没有拼写检查)
                namespace("android") {
                    set("id", R.id.text_view)
                    set("layout_margin", 16.dp)
                    set("layout_gravity", Gravity.CENTER)
                    // ...
                }
            },
            // 你也可以手动指定布局参数
            lpparams = {
                gravity = Gravity.CENTER
            },
            // 执行初始化后的操作
            // 你也可以手动设置属性
            initialize = {
                text = "Hello, World!"
                textSize = 16f
                setTextColor(Color.BLACK)
                // 或者更多操作
                doOnLayout {
                    // ...
                }
            }
        )
        // 使用第三方 View
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
// 获取根布局
val root = hikage.root
// 你还可以将其转换为第一个布局的类型，等价于 hikage.root as LinearLayout
// 得益于 Kotlin 的特性，直接使用 Hikageable(...) { ... }.rootAsType() 可以不需要填写泛型
val root = hikage.rootAsType<LinearLayout>()
// 设置到 Activity 上
setContentView(root)
// 获取构建的布局内部组件 (第一种方案)
val textView = hikage.textView
val button = hikage.button
// 获取构建的布局内部组件 (第二种方案)
val textView = hikage.get<TextView>("text_view")
val button = hikage.get<MaterialButton>("button")
```

## 使用 Android Studio 预览

不同于 XML，Hikage 不支持实时预览，但你可以继承于 Hikage 提供的 `HikagePreview` 在其中传入你的布局，然后在 Android Studio 右侧窗格中查看预览。

你还可以在代码中使用 `isInEditMode` 来避免在预览模式中展示无法显示的实际逻辑代码。

```kotlin
class MyPreview(context: Context, attrs: AttributeSet?) : HikagePreview(context, attrs) {

    override fun onPreview(): Hikage {
        // 返回你的布局
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

注意 `HikagePreview` 仅用于预览，不应该在实际代码中使用，否则会抛出异常。

Hikage 可能会有计划支持 Java，但依然推荐使用 Kotlin。

## WIP

该项目仍在开发中，如果您有任何建议或反馈，请随时开启 `issue` 或 PR。

## 项目推广

<!--suppress HtmlDeprecatedAttribute -->
<div align="center">
    <h2>嘿，还请君留步！👋</h2>
    <h3>这里有 Android 开发工具、UI 设计、Gradle 插件、Xposed 模块和实用软件等相关项目。</h3>
    <h3>如果下方的项目能为你提供帮助，不妨为我点个 star 吧！</h3>
    <h3>所有项目免费、开源，遵循对应开源许可协议。</h3>
    <h1><a href="https://github.com/fankes/fankes/blob/main/project-promote/README-zh-CN.md">→ 查看更多关于我的项目，请点击这里 ←</a></h1>
</div>

## Star History

![Star History Chart](https://api.star-history.com/svg?repos=BetterAndroid/Hikage&type=Date)

## 许可证

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

版权所有 © 2019 HighCapable