# 快速开始

> 集成 `Hikage` 到你的项目中。

## 项目要求

项目需要使用 `Android Studio` 或 `IntelliJ IDEA` 创建且类型为 Android 或 Kotlin Multiplatform 项目并已集成 Kotlin 环境依赖。

- Android Studio (建议 [从这里](https://developer.android.com/studio) 获取最新版本)

- IntelliJ IDEA (建议 [从这里](https://www.jetbrains.com/idea) 获取最新版本)

- Kotlin 1.9.0+、Gradle 8+、Java 17+、Android Gradle Plugin 8+

### 配置存储库

`HikageCompat` 的依赖发布在 **Maven Central** 中，你可以使用如下方式配置存储库。

我们推荐使用 Kotlin DSL 作为 Gradle 构建脚本语言并推荐使用 [SweetDependency](https://github.com/HighCapable/SweetDependency) 来管理依赖。

#### SweetDependency (推荐)

在你的项目 `SweetDependency` 配置文件中配置存储库。

```yaml
repositories:
  google:
  maven-central:
```

#### 传统方式

在你的项目 `build.gradle.kts` 中配置存储库。

```kotlin
repositories {
    google()
    mavenCentral()
}
```

### 配置 Java 版本

在你的项目 `build.gradle.kts` 中修改 Kotlin 的 Java 版本为 17 及以上。

```kt
android {
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}
```

## 功能一览

整个项目分为多个模块，你可以选择你希望引入的模块作为依赖应用到你的项目中，但一定要包含 **hikage-core** 模块。

你可以点击下方对应的模块前往查看详细的功能介绍。

- [hikage-core](../library/hikage-core.md)
- [hikage-compiler](../library/hikage-compiler.md)
- [hikage-recyclerview](../library/hikage-widget-material.md)
- [hikage-extension](../library/hikage-extension.md)
- [hikage-extension-betterandroid](../library/hikage-extension-betterandroid.md)
- [hikage-extension-compose](../library/hikage-extension-compose.md)
- [hikage-widget-androidx](../library/hikage-widget-androidx.md)
- [hikage-widget-material](../library/hikage-widget-material.md)

## Demo

你可以在 [这里](repo://tree/main/samples) 找到一些示例，查看对应的演示项目来更好地了解这些功能的运作方式，快速地挑选出你需要的功能。