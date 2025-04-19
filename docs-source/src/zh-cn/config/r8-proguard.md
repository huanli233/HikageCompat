# R8 与 Proguard 混淆

> 大部分场景下应用程序安装包可通过混淆压缩体积，这里介绍了混淆规则的配置方法。

`Hikage` 不需要额外配置混淆规则，由于 Hikage 装载的 `View` 不需要在 XML 中被定义，它们也可以同样被混淆。

你可以将你的自定义 `View`，例如 `com.yourpackage.YourView` 使用以下规则强制让它们被混淆。

```
-allowobfuscation class com.yourpackage.YourView
```

如果你一定要防止 `Hikage` 被混淆或者混淆后发生了问题，那么你可以使用以下规则来防止 `Hikage` 被混淆。

```
-keep class com.highcapable.hikage**
```