plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android.extensions")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdkVersion(28)
    defaultConfig {
        applicationId = "com.gitlab.pymba86.comfyflat.mobile"
        minSdkVersion(19)
        targetSdkVersion(28)
        versionCode = 1
        versionName = "0.0.1"
        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"

        defaultConfig {
            buildConfigField("String", "ORIGIN_GITLAB_ENDPOINT", "\"https://gitlab.com/\"")


            javaCompileOptions {
                annotationProcessorOptions {
                    arguments = mapOf(
                        "toothpick_registry_package_name" to "ru.terrakok.gitlabclient"
                    )
                }
            }
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                file("proguard-rules.pro")
            )
        }
    }
}

dependencies {

    val supportLibraryVersion = "28.0.0"
    val moxyVersion = "1.4.6"
    val toothpickVersion = "1.0.6"

    // Support
    implementation("com.android.support:appcompat-v7:$supportLibraryVersion")
    implementation("com.android.support:design:$supportLibraryVersion")
    implementation("com.android.support:cardview-v7:$supportLibraryVersion")
    implementation("com.android.support.constraint:constraint-layout:1.1.3")

    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${extra["kotlinVersion"] as String}")

    // Log
    implementation("com.jakewharton.timber:timber:4.7.0")

    // MVP Moxy
    kapt("com.arello-mobile:moxy-compiler:$moxyVersion")
    implementation("com.arello-mobile:moxy-app-compat:$moxyVersion")

    // Cicerone Navigation
    implementation("ru.terrakok.cicerone:cicerone:4.0.2")

    // DI
    implementation("com.github.stephanenicolas.toothpick:toothpick-runtime:$toothpickVersion")
    kapt("com.github.stephanenicolas.toothpick:toothpick-compiler:$toothpickVersion")

    // Gson
    implementation("com.google.code.gson:gson:2.8.2")

    // RxJava
    implementation("io.reactivex.rxjava2:rxandroid:2.1.0")
    implementation("io.reactivex.rxjava2:rxjava:2.2.3")
    implementation("com.jakewharton.rxrelay2:rxrelay:2.1.0")

}

gradle.buildFinished {
    println("VersionName: ${android.defaultConfig.versionName}")
    println("VersionCode: ${android.defaultConfig.versionCode}")
}

configurations.all {
    resolutionStrategy {
        force("org.jetbrains.kotlin:kotlin-stdlib:${extra["kotlinVersion"] as String}")
    }
}