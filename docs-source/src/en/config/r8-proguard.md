# R8 & Proguard Obfuscate

> In most scenarios, the app packages can be compressed through obfuscation,
> here is an introduction to how to configure obfuscation rules.

Generally, you need to use the following rules to avoid the constructor being removed to avoid possible `View` or `LayoutParams` creation failures.

```
-keepclassmembers class * extends android.view.View {
<init>(android.content.Context);
<init>(android.content.Context, android.util.AttributeSet);
}
-keepclassmembers class * extends android.view.ViewGroup$LayoutParams {
<init>(int, int);
}
```

You can force them to be confused with your custom `View`, such as `com.yourpackage.YourView`, using the following rules.

```
-allowobfuscation class com.yourpackage.YourView
```

If you must prevent `Hikage` from being confused or something that occurs after being confused, you can use the following rules to prevent `Hikage` from being confused.

```
-keep class com.highcapable.hikage**
```