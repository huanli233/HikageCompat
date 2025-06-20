# R8 与 Proguard 混淆

> 大部分场景下应用程序安装包可通过混淆压缩体积，这里介绍了混淆规则的配置方法。

一般来说，你需要使用以下规则避免构造方法被去除，以避免可能的 `View` 或 `LayoutParams` 创建失败。

```
-keepclassmembers class * extends android.view.View {
    <init>(android.content.Context);
    <init>(android.content.Context, android.util.AttributeSet);
}
-keepclassmembers class * extends android.view.ViewGroup$LayoutParams {
    <init>(int, int);
}
```

你可以将你的自定义 `View`，例如 `com.yourpackage.YourView` 使用以下规则强制让它们被混淆。

```
-allowobfuscation class com.yourpackage.YourView
```

如果你要防止 `Hikage` 被混淆，那么你可以使用以下规则来防止 `Hikage` 被混淆。

```
-keep class com.highcapable.hikage**
```