buildscript {
  val libPackageMode: String by project
  extra["isPackageMode"] = libPackageMode.toBoolean()
  println("isPackageMode: ${extra["isPackageMode"]}")
}

plugins {
  id("com.android.library")
  id("org.jetbrains.kotlin.android")
  id("com.facebook.react")
  `maven-publish`
  id("com.kezong.fat-aar")
}

val libPackageMode: String by project
val isPackageMode = libPackageMode.toBoolean()

/**
 * This is the configuration block to customize your React Native Android app.
 * By default you don't need to apply any configuration, just uncomment the lines you need.
 */
react {
  val rn = "../reactnative"
  /* Folders */
  //   The root of your project, i.e. where "package.json" lives. Default is '../..'
  root = file(rn)
  //   The folder where the react-native NPM package is. Default is ../../node_modules/react-native
  reactNativeDir = file("$rn/node_modules/react-native")
  //   The folder where the react-native Codegen package is. Default is ../../node_modules/@react-native/codegen
  codegenDir = file("$rn/node_modules/@react-native/codegen")
  //   The cli.js file which is the React Native CLI entrypoint. Default is ../../node_modules/react-native/cli.js
  cliFile = file("$rn/node_modules/react-native/cli.js")

  /* Variants */
  //   The list of variants to that are debuggable. For those we're going to
  //   skip the bundling of the JS bundle and the assets. By default is just 'debug'.
  //   If you add flavors like lite, prod, etc. you'll have to list your debuggableVariants.
  // debuggableVariants = ["liteDebug", "prodDebug"]

  /* Bundling */
  //   A list containing the node command and its flags. Default is just 'node'.
  // nodeExecutableAndArgs = ["node"]
  //
  //   The command to run when bundling. By default is 'bundle'
  // bundleCommand = "ram-bundle"
  //
  //   The path to the CLI configuration file. Default is empty.
  // bundleConfig = file(../rn-cli.config.js)
  //
  //   The name of the generated asset file containing your JS bundle
  // bundleAssetName = "MyApplication.android.bundle"
  //
  //   The entry file for bundle generation. Default is 'index.android.js' or 'index.js'
  // entryFile = file("../js/MyApplication.android.js")
  //
  //   A list of extra flags to pass to the 'bundle' commands.
  //   See https://github.com/react-native-community/cli/blob/main/docs/commands.md#bundle
  // extraPackagerArgs = []

  /* Hermes Commands */
  //   The hermes compiler command to run. By default it is 'hermesc'
  // hermesCommand = "$rootDir/my-custom-hermesc/bin/hermesc"
  //
  //   The list of flags to pass to the Hermes compiler. By default is "-O", "-output-source-map"
  // hermesFlags = ["-O", "-output-source-map"]

  /* Autolinking with :lib library */
  if (isPackageMode) {
    autolinkLibrariesWithLibrary()
  }
}

android {
  namespace = "com.reactandroidprojectstructuredemo.lib"
  compileSdk = rootProject.ext["compileSdkVersion"].toString().toInt()

  defaultConfig {
    minSdk = rootProject.ext["minSdkVersion"].toString().toInt()

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    consumerProguardFiles("consumer-rules.pro")
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
  externalNativeBuild {
    cmake {
      // using the higher version of cmake to avoid file name length limitation of 260 chars
      version = "3.31.5"
    }
  }
  ndkVersion = "28.0.13004108"

  publishing {
    singleVariant("release") {
    }
  }
}

dependencies {
  implementation("com.facebook.react:react-android:0.78.0")
}

publishing {

  publications.register<MavenPublication>("localTest") {
    repositories {
      maven { url = uri(layout.buildDirectory.dir("repo")) }
    }
    groupId = "com.reactandroidprojectstructuredemo"
    artifactId = "lib"
    version = "0.0.1"

    afterEvaluate {
      from(components["release"])
    }
  }
}

fataar {
  transitive = true
}