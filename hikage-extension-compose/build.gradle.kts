plugins {
    autowire(libs.plugins.android.library)
    autowire(libs.plugins.kotlin.android)
    autowire(libs.plugins.compose.compiler)
    autowire(libs.plugins.kotlin.dokka)
    autowire(libs.plugins.maven.publish)
}

group = property.project.groupName
version = property.project.hikage.extension.compose.version

android {
    namespace = property.project.hikage.extension.compose.namespace
    compileSdk = property.project.android.compileSdk

    defaultConfig {
        minSdk = property.project.android.compose.minSdk
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
}

dependencies {
    implementation(projects.hikageCore)
    implementation(com.highcapable.yukireflection.api)
    implementation(com.huanli233.betterandroid.compat.ui.extension)
    implementation(com.huanli233.betterandroid.compat.system.extension)
    implementation(androidx.core.core.ktx)
    implementation(androidx.appcompat.appcompat)
    implementation(androidx.compose.ui.ui)
    testImplementation(junit.junit)
    androidTestImplementation(androidx.test.ext.junit)
    androidTestImplementation(androidx.test.espresso.espresso.core)
}