# Quick Start

> Integrate `Hikage` into your project.

## Project Requirements

The project needs to be created using `Android Studio` or `IntelliJ IDEA` and be of type Android or Kotlin Multiplatform
project and have integrated Kotlin environment dependencies.

- Android Studio (It is recommended to get the latest version [from here](https://developer.android.com/studio))

- IntelliJ IDEA (It is recommended to get the latest version [from here](https://www.jetbrains.com/idea))

- Kotlin 1.9.0+, Gradle 8+, Java 17+, Android Gradle Plugin 8+

### Configure Repositories

The dependencies of `Hikage` are published in **Maven Central**,
you can use the following method to configure repositories.

We recommend using Kotlin DSL as the Gradle build script language and [SweetDependency](https://github.com/HighCapable/SweetDependency)
to manage dependencies.

#### SweetDependency (Recommended)

Configure repositories in your project's `SweetDependency` configuration file.

```yaml
repositories:
  google:
  maven-central:
```

#### Traditional Method

Configure repositories in your project `build.gradle.kts`.

```kotlin
repositories {
    google()
    mavenCentral()
}
```

### Configure Java Version

Modify the Java version of Kotlin in your project `build.gradle.kts` to 17 or above.

> Kotlin DSL

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

## Functional Overview

The project is divided into multiple modules. You can choose the module you wish to include as a dependency in your project, but be sure to include the **hikage-core** module.

Click the corresponding module below to view detailed feature descriptions.

- [hikage-core](../library/hikage-core.md)
- [hikage-compiler](../library/hikage-compiler.md)
- [hikage-recyclerview](../library/hikage-recyclerview.md)
- [hikage-extension](../library/hikage-extension.md)
- [hikage-extension-betterandroid](../library/hikage-extension-betterandroid.md)
- [hikage-extension-compose](../library/hikage-extension-compose.md)
- [hikage-widget-androidx](../library/hikage-widget-androidx.md)
- [hikage-widget-material](../library/hikage-widget-material.md)

## Demo

You can find some samples [here](repo://tree/main/samples) view the corresponding demo project to better understand how these functions work and quickly
select the functions you need.