# hikage-extension-compose

![Maven Central](https://img.shields.io/maven-central/v/com.huqnli233.hikage.compat/hikage-extension-compose?logo=apachemaven&logoColor=orange)
<span style="margin-left: 5px"/>
![Android Min SDK](https://img.shields.io/badge/Min%20SDK-14-orange?logo=android)

This is a Hikage extension dependency for Jetpack Compose component-related features.

## Configure Dependency

You can add this module to your project using the following method.

::: warning

This module relies on the Jetpack Compose compiler plugin.
Please make sure that your project has integrated Jetpack Compose-related dependencies.
Please refer to [here](https://developer.android.com/develop/ui/compose/compiler) for details.

:::

### SweetDependency (Recommended)

Add dependency in your project's `SweetDependency` configuration file.

```yaml
libraries:
  com.huanli233.hikage.compat:
    hikage-extension-compose:
      version: +
```

Configure dependency in your project `build.gradle.kts`.

```kotlin
implementation(com.huanli233.hikage.compat.hikage.extension.compose)
```

### Traditional Method

Configure dependency in your project `build.gradle.kts`.

```kotlin
implementation("com.huanli233.hikage.compat:hikage-extension-compose:<version>")
```

Please change `<version>` to the version displayed at the top of this document.

## Function Introduction

You can view the KDoc [click here](kdoc://hikage-extension-compose).

### Use Jetpack Compose in Hikage

You can use the following methods to embed Jetpack Compose components in a Hikage layout.

> The following example

```kotlin
Hikageable {
   ComposeView(
       lparams = LayoutParams(matchParent = true)
   ) {
       Text("Hello, World!")
   }
}
```

### Use Hikage in Jetpack Compose

You can use the following methods to embed Hikage components in a Jetpack Compose layout.

> The following example

```kotlin
Column(
   modifier = Modifier.fillMaxSize()
) {
    HikageView {
        TextView(
            lparams = LayoutParams(matchParent = true)
        ) {
            text = "Hello, World!"
            textSize = 20f
        }
    }
}
```