# hikage-extension-betterandroid

![Maven Central](https://img.shields.io/maven-central/v/com.huanli233.hikage.compat/hikage-extension-betterandroid?logo=apachemaven&logoColor=orange)
<span style="margin-left: 5px"/>
![Android Min SDK](https://img.shields.io/badge/Min%20SDK-14-orange?logo=android)

This is a Hikage extension dependency for [BetterAndroid](https://github.com/BetterAndroid/BetterAndroid) UI component-related features.

## Configure Dependency

You can add this module to your project using the following method.

### SweetDependency (Recommended)

Add dependency in your project's `SweetDependency` configuration file.

```yaml
libraries:
  com.highcapable.hikage:
    hikage-extension-betterandroid:
      version: +
```

Configure dependency in your project `build.gradle.kts`.

```kotlin
implementation(com.highcapable.hikage.hikage.extension.betterandroid)
```

### Traditional Method

Configure dependency in your project `build.gradle.kts`.

```kotlin
implementation("com.highcapable.hikage:hikage-extension-betterandroid:<version>")
```

Please change `<version>` to the version displayed at the top of this document.

## Function Introduction

You can view the KDoc [click here](kdoc://hikage-extension-betterandroid).

### Adapter Extension

Hikage provides layout extension function for BetterAndroid's [Adapter](https://betterandroid.github.io/BetterAndroid/en/library/ui-component#adapter),
you can use the Hikage layout directly on the original extension method of the adapter.

It uses the `ViewHolderDelegate` provided by BetterAndroid to create extension methods.

Here is a simple example based on `RecyclerView`.

> The following example

```kotlin
// Assume this is the dataset you need to bind to.
val listData = ArrayList<CustomBean>()
// Create and bind to a custom RecyclerView.Adapter.
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