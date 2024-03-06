import java.util.Properties

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.googleServices)
}

android {
    namespace = "by.vfedorenko.wishlist.android"
    compileSdk = 34
    defaultConfig {
        applicationId = "by.vfedorenko.wishlist.android"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }
    signingConfigs {
        getByName("debug") {
            var password = System.getenv("DEBUG_KEYSTORE_PASSWORD")
            if (password.isNullOrBlank()) {
                password = getLocalPropertyString("DEBUG_KEYSTORE_PASSWORD")
            }

            storeFile = file("debug_keystore")
            keyAlias = "Android"
            storePassword = password
            keyPassword = password
        }

//        create("release") {
//            var password = System.getenv("KEYSTORE_PASSWORD")
//            if (password.isNullOrBlank()) {
//                password = getLocalPropertyString("KEYSTORE_PASSWORD")
//            }
//
//            storeFile = file("upload_keystore")
//            keyAlias = "Android"
//            storePassword = password
//            keyPassword = password
//        }
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        debug {
            isMinifyEnabled = false

            signingConfig = signingConfigs.getByName("debug")
        }

        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(projects.shared)
    implementation(libs.bundles.androidAppImplementation)

    ksp(libs.hilt.compiler)

    debugImplementation(libs.compose.ui.tooling)
}

fun getLocalPropertyString(propertyName: String): String = try {
    readProperties(
        file("${rootProject.projectDir}/local.properties")
    ).getProperty(propertyName) ?: ""
} catch (e: Exception) {
    println("Failed to open Local Properties")
    ""
}

fun readProperties(propertiesFile: File) = Properties().apply {
    propertiesFile.inputStream().use { load(it) }
}
