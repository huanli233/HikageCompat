# hikage-widget-androidx

![Maven Central](https://img.shields.io/maven-central/v/com.huanli233.hikage.compat/hikage-widget-androidx?logo=apachemaven&logoColor=orange)
<span style="margin-left: 5px"/>
![Android Min SDK](https://img.shields.io/badge/Min%20SDK-/14-orange?logo=android)

This is a Hikage extension dependency for Jetpack Compact component-related features.

## Configure Dependency

You can add this module to your project using the following method.

### SweetDependency (Recommended)

Add dependency in your project's `SweetDependency` configuration file.

```yaml
libraries:
  com.huanli233.hikage.compat:
    hikage-widget-androidx:
      version: +
```

Configure dependency in your project `build.gradle.kts`.

```kotlin
implementation(com.huanli233.hikage.compat.hikage.widget.androidx)
```

### Traditional Method

Configure dependency in your project `build.gradle.kts`.

```kotlin
implementation("com.huanli233.hikage.compat:hikage-widget-androidx:<version>")
```

Please change `<version>` to the version displayed at the top of this document.

## Function Introduction

This dependency inherits the available components from Jetpack Compact, which you can directly reference to use in Hikage.

> The following example

```kotlin
LinearLayoutCompact(
    lparams = LayoutParams(matchParent = true) {
        topMargin = 16.dp
    },
    init = {
        orientation = LinearLayoutCompat.VERTICAL
        gravity = Gravity.CENTER
    }
) {
    AppCompatTextView {
        text = "Hello, World!"
        textSize = 16f
        gravity = Gravity.CENTER
    }
}
```