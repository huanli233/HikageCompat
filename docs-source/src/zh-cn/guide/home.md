# 介绍

> `Hikage` (发音 /ˈhɪkɑːɡeɪ/) 是一个 Android 响应式 UI 构建工具。

## 背景

这是一个 Android 响应式 UI 构建工具，它的设计聚焦于 **实时代码构建 UI**。

项目图标由 [MaiTungTM](https://github.com/Lagrio) 设计，名称取自 「BanG Dream It's MyGO!!!!!」 中的原创歌曲《春日影》(Haru**hikage**)。

<details><summary>为什么要...</summary>
  <div align="center">
  <img src="/images/nagasaki_soyo.png" width = "100" height = "100" alt="LOGO"/>

  **なんで春日影レイアウト使いの？**
  </div>
</details>

不同于 Jetpack Compose 的声明式 UI，Hikage 专注于 Android 原生平台，它的设计目标是为了让开发者能够快速构建 UI 并可直接支持 Android 原生组件。

**<u>Hikage 只是一个 UI 构建工具，自身并不提供任何 UI 组件</u>**。

拒绝重复造轮子，我们的方案始终是兼容与高效，现在你可以抛弃 ViewBinding 和 XML 甚至是 `findViewById`，直接来尝试使用代码布局吧。

`Hikage` 配合我们的另一个项目 [BetterAndroid](https://github.com/BetterAndroid/BetterAndroid) 使用效果更佳，同时 `Hikage` 自身将自动引用 `BetterAndroid` 相关依赖作为核心内容。

## 用途

Hikage 主要适用于专注原生 Android 平台开发的开发者，自从 Kotlin 作为主要开发语言后，依然没有一套比较完美的工具能够使用 DSL 实现动态代码布局，
所以没有使用 Jetpack Compose 的项目依然需要使用原始的 XML，虽然有着 ViewBinding 的支持，但是依然不是很友好。

Hikage 继承了 [Anko](https://github.com/Kotlin/anko)、[Splitties](https://github.com/LouisCAD/Splitties) 的设计方案以及借鉴了 Jetpack Compose 的 DSL 函数命名方案，
并且在此基础上进行了大量改进，使得它在使用成本上更贴近原生，写法上更贴近 Jetpack Compose。

> 各种 DSL 布局方案对比

:::: code-group
::: code-group-item Hikage

```kotlin
LinearLayout(
    lparams = LayoutParams(matchParent = true) {
        topMargin = 16.dp
    },
    init = {
        orientation = LinearLayout.VERTICAL
        gravity = Gravity.CENTER
    }
) {
    TextView {
        text = "Hello, World!"
        textSize = 16f
        gravity = Gravity.CENTER
    }
}
```

:::
::: code-group-item Anko、Splitties

```kotlin
verticalLayout {
    gravity = Gravity.CENTER
    textView("Hello, World!") {
        textSize = 16f
        gravity = Gravity.CENTER
    }
}.lparams(
    width = matchParent,
    height = matchParent
) {
    topMargin = dip(16)
}
```

:::
::: code-group-item Jetpack Compose

```kotlin
Column(
    modifier = Modifier.padding(top = 16.dp),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
) {
    Text(
        text = "Hello, World!",
        fontSize = 16.sp,
        textAlign = TextAlign.Center
    )
}
```

:::
::::

Hikage 的基础部分**完全不需要借助外部及额外的编译插件**，它能**即插即用**并在**任何地方创建**一个可被设置到父布局以及 `Window` 上的 `View` 对象。

Hikage **全面兼容**混合式布局，你可以在 Hikage 中嵌入 XML (使用 `R.layout` 方案装载布局)、ViewBinding 甚至是 Jetpack Compose。

## 语言要求

推荐使用 Kotlin 作为首选开发语言，本项目完全使用 Kotlin 编写，且不再有计划兼容 Java。

文档全部的 Demo 示例代码都将使用 Kotlin 进行描述，如果你完全不会使用 Kotlin，那么你将有可能无法正常使用本项目。

## 功能贡献

本项目的维护离不开各位开发者的支持和贡献，目前这个项目处于初期阶段，可能依然存在一些问题或者缺少你需要的功能，
如果可能，欢迎提交 PR 为此项目贡献你认为需要的功能或前往 [GitHub Issues](repo://issues) 向我们提出建议。