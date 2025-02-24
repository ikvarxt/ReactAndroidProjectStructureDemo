plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.reactandroidprojectstructuredemo.app_bare"
    compileSdk = rootProject.extra["compileSdkVersion"].toString().toInt()

    defaultConfig {
        applicationId = "com.reactandroidprojectstructuredemo.app_bare"
        minSdk = 26
        targetSdk = rootProject.extra["targetSdkVersion"].toString().toInt()
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    packaging {
        jniLibs {
            pickFirsts += setOf(
                "**/libjsi.so",
                "**/libc++_shared.so",
                "**/libreact_render*.so",
                "**/libreactnativejni.so",
                "**/libreact_performance_timeline.so",
                "**/libfbjni.so",
                "**/libreactnative.so",
            )
        }

    }
}

repositories {
    maven {
        url = uri(project(":lib").layout.buildDirectory.dir("repo"))
        content {
            includeGroup("com.reactandroidprojectstructuredemo")
        }
    }
}

dependencies {

    api("com.reactandroidprojectstructuredemo:lib:0.0.1")

    implementation("com.facebook.react:react-android:0.78.0")
    implementation("com.facebook.react:hermes-android:0.78.0")

    // TODO: 2/24/2025 should auto implement from lib aar
    implementation ("androidx.coordinatorlayout:coordinatorlayout:1.2.0")
    implementation ("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    implementation( "com.google.android.material:material:1.6.1")
}