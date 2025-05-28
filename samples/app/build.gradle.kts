plugins {
    autowire(libs.plugins.android.application)
    autowire(libs.plugins.kotlin.android)
    autowire(libs.plugins.kotlin.ksp)
}

android {
    namespace = property.project.samples.app.packageName
    compileSdk = property.project.android.compileSdk

    defaultConfig {
        applicationId = property.project.samples.app.packageName
        minSdk = property.project.android.minSdk
        targetSdk = property.project.android.targetSdk
        versionName = property.project.samples.app.versionName
        versionCode = property.project.samples.app.versionCode
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        multiDexEnabled = true
    }
    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
        freeCompilerArgs = listOf(
            "-Xno-param-assertions",
            "-Xno-call-assertions",
            "-Xno-receiver-assertions"
        )
    }
    buildFeatures {
        buildConfig = true
        viewBinding = true
    }
}

dependencies {
    ksp(projects.hikageCompiler)
    implementation(projects.hikageCore)
    implementation(projects.hikageExtension)
    implementation(projects.hikageExtensionBetterandroid)
    implementation(projects.hikageWidgetAndroidx)
    implementation(projects.hikageWidgetMaterial)
//    implementation(com.highcapable.pangutext.pangutext.android) {
//        exclude(group = "com.highcapable.betterandroid")
//    }
    implementation(com.huanli233.betterandroid.compat.ui.component)
    implementation(com.huanli233.betterandroid.compat.ui.extension)
    implementation(com.huanli233.betterandroid.compat.system.extension)
    implementation(androidx.core.core.ktx)
    implementation(androidx.appcompat.appcompat)
    implementation(androidx.multidex.multidex)
    testImplementation(junit.junit)
    androidTestImplementation(androidx.test.ext.junit)
    androidTestImplementation(androidx.test.espresso.espresso.core)
}