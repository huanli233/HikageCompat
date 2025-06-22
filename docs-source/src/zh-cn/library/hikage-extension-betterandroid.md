# hikage-extension-betterandroid

![Maven Central](https://img.shields.io/maven-central/v/com.huanli233.hikage.compat/hikage-extension-betterandroid?logo=apachemaven&logoColor=orange)
<span style="margin-left: 5px"/>
![Android Min SDK](https://img.shields.io/badge/Min%20SDK-14-orange?logo=android)

这是 Hikage 针对 [BetterAndroid](https://github.com/BetterAndroid/BetterAndroid) UI 组件相关功能的扩展依赖。

## 配置依赖

你可以使用如下方式将此模块添加到你的项目中。

### SweetDependency (推荐)

在你的项目 `SweetDependency` 配置文件中添加依赖。

```yaml
libraries:
  com.highcapable.hikage:
    hikage-extension-betterandroid:
      version: +
```

在你的项目 `build.gradle.kts` 中配置依赖。

```kotlin
implementation(com.highcapable.hikage.hikage.extension.betterandroid)
```

### 传统方式

在你的项目 `build.gradle.kts` 中配置依赖。

```kotlin
implementation("com.highcapable.hikage:hikage-extension-betterandroid:<version>")
```

请将 `<version>` 修改为此文档顶部显示的版本。

## 功能介绍

你可以 [点击这里](kdoc://hikage-extension-betterandroid) 查看 KDoc。

### 适配器 (Adapter) 扩展

Hikage 为 BetterAndroid 提供的 [适配器](https://betterandroid.github.io/BetterAndroid/zh-cn/library/ui-component#%E9%80%82%E9%85%8D%E5%99%A8-adapter)
提供了布局扩展功能，你可以直接在适配器的原始扩展方法上使用 Hikage 布局。

它使用了 BetterAndroid 提供的 `ViewHolderDelegate` 来创建扩展方法。

下面提供了一个基于 `RecyclerView` 的简单示例。

> 示例如下

```kotlin
// 假设这就是你需要绑定的数据集
val listData = ArrayList<CustomBean>()
// 创建并绑定到自定义的 RecyclerView.Adapter
val adapter = recyclerView.bindAdapter<CustomBean> {
    onBindData { listData }
    onBindItemView(
        Hikageable = {
            TextView(id = "text_view") {
                text = "Hello, World!"
                textSize = 16f
            }
        }
    ) { hikage, bean, position ->
        hikage.get<TextView>("text_view").text = bean.name
    }
}
```