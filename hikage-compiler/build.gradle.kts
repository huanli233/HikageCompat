plugins {
    autowire(libs.plugins.kotlin.jvm)
    autowire(libs.plugins.kotlin.ksp)
    autowire(libs.plugins.maven.publish)
    id("java-gradle-plugin")
//    id("com.github.gmazzo.buildconfig") version "5.6.6"
}

group = property.project.groupName
version = property.project.hikage.compiler.version

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    jvmToolchain(17)
    compilerOptions {
        freeCompilerArgs = listOf(
            "-Xno-param-assertions",
            "-Xno-call-assertions",
            "-Xno-receiver-assertions"
        )
    }
}

gradlePlugin {
    plugins {
        create("hikage-compiler") {
            id = "com.huanli233.hikage-compiler"
            implementationClass = "com.highcapable.hikage.compiler.HikageProcessor"
        }
    }
}

dependencies {
    implementation(gradleApi())
    compileOnly(com.google.devtools.ksp.symbol.processing.api)
    ksp(dev.zacsweers.autoservice.auto.service.ksp)
    implementation(com.google.auto.service.auto.service.annotations)
    implementation(com.squareup.kotlinpoet)
    implementation(com.squareup.kotlinpoet.ksp)

    implementation(org.jetbrains.kotlin.kotlin.stdlib)
    implementation(org.jetbrains.kotlin.kotlin.gradle.plugin.api)
    implementation(org.jetbrains.kotlin.kotlin.compiler.embeddable)
    testImplementation("com.github.tschuchortdev:kotlin-compile-testing:1.5.0")
}