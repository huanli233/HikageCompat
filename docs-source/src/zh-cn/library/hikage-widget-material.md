# hikage-widget-material

![Maven Central](https://img.shields.io/maven-central/v/com.huanli233.hikage.compat/hikage-widget-material?logo=apachemaven&logoColor=orange)
<span style="margin-left: 5px"/>
![Android Min SDK](https://img.shields.io/badge/Min%20SDK-21-orange?logo=android)

这是 Hikage 针对 Google Material (MDC) 组件相关功能的扩展依赖。

## 配置依赖

你可以使用如下方式将此模块添加到你的项目中。

### SweetDependency (推荐)

在你的项目 `SweetDependency` 配置文件中添加依赖。

```yaml
libraries:
  com.huanli233.hikage.compat:
    hikage-widget-material:
      version: +
```

在你的项目 `build.gradle.kts` 中配置依赖。

```kotlin
implementation(com.huanli233.hikage.compat.hikage.widget.material)
```

### 传统方式

在你的项目 `build.gradle.kts` 中配置依赖。

```kotlin
implementation("com.huanli233.hikage.compat:hikage-widget-material:<version>")
```

请将 `<version>` 修改为此文档顶部显示的版本。

## 功能介绍

这个依赖中继承了来自 Google Material (MDC) 中的可用组件，你可以直接引用它们到 Hikage 中使用。

> 示例如下

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