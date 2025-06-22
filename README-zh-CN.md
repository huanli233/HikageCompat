# Hikage Compat

[![GitHub license](https://img.shields.io/github/license/BetterAndroid/Hikage?color=blue)](https://github.com/huanli233/HikageCompat/blob/main/LICENSE)
[![Telegram](https://img.shields.io/badge/discussion-Telegram-blue.svg?logo=telegram)](https://t.me/BetterAndroid)
[![Telegram](https://img.shields.io/badge/discussion%20dev-Telegram-blue.svg?logo=telegram)](https://t.me/HighCapable_Dev)
[![QQ](https://img.shields.io/badge/discussion%20dev-QQ-blue.svg?logo=tencent-qq&logoColor=red)](https://qm.qq.com/cgi-bin/qm/qr?k=Pnsc5RY6N2mBKFjOLPiYldbAbprAU3V7&jump_from=webapi&authKey=X5EsOVzLXt1dRunge8ryTxDRrh9/IiW1Pua75eDLh9RE3KXE+bwXIYF5cWri/9lf)

<img src="img-src/icon.svg" width = "100" height = "100" alt="LOGO"/>

一个 Android 响应式 UI 构建工具。(forked)

[English](README.md) | 简体中文

## 这是什么

`Hikage` (发音 /ˈhɪkɑːɡeɪ/)，这是一个 Android 响应式 UI 构建工具，它的设计聚焦于 **实时代码构建 UI**。

项目图标由 [MaiTungTM](https://github.com/Lagrio) 设计，名称取自 「BanG Dream It's MyGO!!!!!」 中的原创歌曲《春日影》(Haru**hikage**)。

<details><summary>为什么要...</summary>
  <div align="center">
  <img src="img-src/nagasaki_soyo.png" width = "100" height = "100" alt="LOGO"/>

**なんで春日影レイアウト使いの？**
  </div>
</details>

不同于 Jetpack Compose 的声明式 UI，Hikage 专注于 Android 原生平台，它的设计目标是为了让开发者能够快速构建 UI 并可直接支持 Android 原生组件。

**<u>Hikage 只是一个 UI 构建工具，自身并不提供任何 UI 组件</u>**。

拒绝重复造轮子，我们的方案始终是兼容与高效，现在你可以抛弃 ViewBinding 和 XML 甚至是 `findViewById`，直接来尝试使用代码布局吧。

`Hikage` 配合另一个项目 [BetterAndroid](https://github.com/BetterAndroid/BetterAndroid) 使用效果更佳，同时 `Hikage` 自身将自动引用
`BetterAndroid` 相关依赖作为核心内容。

## 关于此分支

此 fork 使非 Compose 模块与 API 14 兼容，并修复了一些问题、添加了一些功能。

- 改进状态
- 使用 XML 中定义的视图属性（只需将属性添加到任何布局文件的根 View，并通过 attr 参数指定它）
- 缓存 AttributeSet，减少反射
- 修复 LayoutParams Wrapper 在 ConstraintLayout 或其他一些布局中无法正常工作的问题
- 添加某些函数的别名
- 改进了字符串 ID 和 Int ID 映射

## 开始使用

[点击这里](https://huanli233.github.io/HikageCompat/zh-cn) 前往文档页面查看更多详细教程和内容。

## Star History

![Star History Chart](https://api.star-history.com/svg?repos=huanli233/HikageCompat&type=Date)

## 第三方开源使用声明

- [AndroidHiddenApiBypass](https://github.com/LSPosed/AndroidHiddenApiBypass)

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
