# hikage-compiler

![Maven Central](https://img.shields.io/maven-central/v/com.huanli233.hikage.compat/hikage-compiler?logo=apachemaven&logoColor=orange)
<span style="margin-left: 5px"/>
![Android Min SDK](https://img.shields.io/badge/Min%20SDK-14-orange?logo=android)

This is a Hikage automatic compilation module.

## Configure Dependency

You can add this module to your project using the following method.

::: warning

You need to integrate the [Google KSP](https://github.com/google/ksp/releases) plugin in your project that is suitable for the current Kotlin version of your project.

:::

### SweetDependency (Recommended)

Add dependency in your project's `SweetDependency` configuration file.

```yaml
plugins:
  com.google.devtools.ksp:
    version: +

libraries:
  com.huanli233.hikage.compat:
    hikage-compiler:
      version: +
```

Configure dependency in your root project `build.gradle.kts`.

```kotlin
plugins {
    // ...
    autowire(libs.plugins.com.google.devtools.ksp) apply false
}
```

Configure dependency in your project `build.gradle.kts`.

```kotlin
plugins {
    // ...
    autowire(libs.plugins.com.google.devtools.ksp)
}

dependencies {
    // ...
    ksp(com.huanli233.hikage.compat.hikage.compiler)
}
```

### Traditional Method

Configure dependency in your root project `build.gradle.kts`.

```kotlin
plugins {
    // ...
    id("com.google.devtools.ksp") version "<ksp-version>" apply false
}
```

Configure dependency in your project `build.gradle.kts`.

```kotlin
plugins {
    // ...
    id("com.google.devtools.ksp")
}

dependencies {
    // ...
    ksp("com.huanli233.hikage.compat:hikage-compiler:<version>")
}
```

Please change `<version>` to the version displayed at the top of this document,
and change `<ksp-version>` to the KSP version corresponding to the Kotlin version currently used by your project.

## Function Introduction

Hikage's compilation module will automatically generate code at runtime.
After update, please re-run the `assembleDebug` or `assembleRelease` task to generate the latest code.

### Generate Layout Components

Hikage can automatically generate the `Hikageable` function corresponding to the layout component for the specified layout component at compile time.

#### Custom View

You can add the `HikageView` annotation on your custom `View` to mark it as a Hikage layout component.

| Parameter Name     | Description                                                                                                                                                                      |
| ------------------ | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `lparams`          | LayoutParams `Class` object, if your custom `View` is a subclass of `ViewGroup`, you can declare or leave it blank to use the default value                                      |
| `alias`            | The alias of the layout component, that is, the function name to be generated, gets the name of the current Class by default                                                     |
| `requireInit`      | Whether to fill in the initialization method block of the layout, the default is the omitted parameters                                                                          |
| `requirePerformer` | Whether to fill in the `performer` method block of the layout, the default is an omitted parameter, which only takes effect when your custom `View` is a subclass of `ViewGroup` |

> The following example

```kotlin
@HikageView(lparams = LinearLayout.LayoutParams::class)
class MyLayout(context: Context, attrs: AttributeSet? = null) : LinearLayout(context, attrs) {
    // ...
}
```

Once compiled, you can use `MyLayout` as the layout component in the Hikage layout.

> The following example

```kotlin
Hikageable {
    MyLayout {
        TextView(
            lparams = LayoutParams {
                topMargin = 16.dp
            }
        ) {
            text = "Hello, World!"
        }
    }
}
```

#### Third-party Components

Hikage can also automatically generate layout component functions for the `View` component provided by third parties, and you can use the `HikageViewDeclaration` annotation to complete it.

| Parameter Name     | Description                                                                                                                                                                      |
| ------------------ | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `view`             | Class object of layout component that needs to be declared                                                                                                                       |
| `lparams`          | LayoutParams `Class` object, if your custom `View` is a subclass of `ViewGroup`, you can declare or leave it blank to use the default value                                      |
| `alias`            | The alias of the layout component, that is, the name of the function to be generated, obtains the name of the `view` Class by default                                            |
| `requireInit`      | Whether to fill in the initialization method block of the layout, the default is the omitted parameters                                                                          |
| `requirePerformer` | Whether to fill in the `performer` method block of the layout, the default is an omitted parameter, which only takes effect when your custom `View` is a subclass of `ViewGroup` |

> The following example

```kotlin
@HikageViewDeclaration(ThirdPartyView::class)
object ThirdPartyViewDeclaration
```

This annotation can be declared on any `object` class and is only used as a class that needs to be automatically included by the annotation scanner. You can set visibility to `private`, but make sure that the annotated class must be modified with `object`.

Similarly, after compilation, you can use `ThirdPartyView` as the layout component in the Hikage layout.

> The following example

```kotlin
Hikageable {
    ThirdPartyView {
        // ...
    }
}
```

::: tip

Hikage The function package name path for generating layout components is `com.highcapable.hikage.widget` + the full package name of your `View` or third-party `View` component.

:::