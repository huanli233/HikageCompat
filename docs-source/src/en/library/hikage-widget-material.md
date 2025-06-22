# hikage-widget-material

![Maven Central](https://img.shields.io/maven-central/v/com.huanli233.hikage.compat/hikage-widget-material?logo=apachemaven&logoColor=orange)
<span style="margin-left: 5px"/>
![Android Min SDK](https://img.shields.io/badge/Min%20SDK-14-orange?logo=android)

This is a Hikage extension dependency for Google Material (MDC) component-related features.

## Configure Dependency

You can add this module to your project using the following method.

### SweetDependency (Recommended)

Add dependency in your project's `SweetDependency` configuration file.

```yaml
libraries:
  com.huanli233.hikage.compat:
    hikage-widget-material:
      version: +
```

Configure dependency in your project `build.gradle.kts`.

```kotlin
implementation(com.huanli233.hikage.compat.hikage.widget.material)
```

### Traditional Method

Configure dependency in your project `build.gradle.kts`.

```kotlin
implementation("com.huanli233.hikage.compat:hikage-widget-material:<version>")
```

Please change `<version>` to the version displayed at the top of this document.

## Function Introduction

This dependency inherits the available components from Google Material (MDC), which you can directly reference to use in Hikage.

> The following example

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
    MaterialTextView {
        text = "Hello, World!"
        textSize = 16f
        gravity = Gravity.CENTER
    }
    MaterialButton {
        text = "Hello, World!"
        textSize = 16f
        gravity = Gravity.CENTER
    }
    TextInputLayout(
        lparams = LayoutParams {
            topMargin = 16.dp
        },
        init = {
            minWidth = 200.dp
            hint = "Enter your text"
        }
    ) {
        TextInputEditText()
    }
}
```