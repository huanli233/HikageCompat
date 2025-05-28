plugins {
    autowire(libs.plugins.android.library)
    autowire(libs.plugins.kotlin.android)
    autowire(libs.plugins.kotlin.dokka)
    autowire(libs.plugins.maven.publish)
    autowire(libs.plugins.kotlin.ksp)
}

group = property.project.groupName
version = property.project.hikage.core.version

android {
    namespace = property.project.hikage.core.namespace
    compileSdk = property.project.android.compileSdk

    defaultConfig {
        minSdk = property.project.android.minSdk
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
            "-opt-in=kotlin.ExperimentalStdlibApi",
            "-Xno-param-assertions",
            "-Xno-call-assertions",
            "-Xno-receiver-assertions"
        )
    }
}

dependencies {
    lintPublish(projects.hikageCoreLint)
    ksp(projects.hikageCompiler)
    implementation(org.lsposed.hiddenapibypass.hiddenapibypass)
    implementation(com.highcapable.yukireflection.api)
    api(com.huanli233.betterandroid.compat.ui.extension)
    implementation(com.huanli233.betterandroid.compat.system.extension)
    implementation(androidx.core.core.ktx)
    implementation(androidx.appcompat.appcompat)
    testImplementation(junit.junit)
    androidTestImplementation(androidx.test.ext.junit)
    androidTestImplementation(androidx.test.espresso.espresso.core)
}