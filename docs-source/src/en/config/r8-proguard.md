# R8 & Proguard Obfuscate

> In most scenarios, the app packages can be compressed through obfuscation,
> here is an introduction to how to configure obfuscation rules.

`Hikage` does not require additional configuration of obfuscation rules, since `View` loaded by Hikage does not need to be defined in XML, they can be equally obfuscated.

You can force them to be confused with your custom `View`, such as `com.yourpackage.YourView`, using the following rules.

```
-allowobfuscation class com.yourpackage.YourView
```

If you must prevent `Hikage` from being confused or something that occurs after being confused, you can use the following rules to prevent `Hikage` from being confused.

```
-keep class com.highcapable.hikage**
```