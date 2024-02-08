plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("realm-android")
}

android {
    namespace = "com.raweng.pagemapper.pagemappersdk"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
    }

    buildFeatures {
        dataBinding = true
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.2"
    }
    sourceSets {
        getByName("main") {
            jni {
                srcDirs("src/main/jni")
            }
        }
    }

    externalNativeBuild {
        cmake {
            path = File("src/main/jni/CMakeLists.txt")
        }
    }
}

dependencies {
    api(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar", "*.aar"))))
    api("com.contentstack.sdk:android:3.10.1")
    api("org.jetbrains.kotlin:kotlin-reflect:1.9.22")
    api( "io.realm:realm-gradle-plugin:10.15.0")
    api("com.google.code.gson:gson:2.10.1")
    api("androidx.core:core-ktx:1.12.0")
    api("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    api("androidx.activity:activity-compose:1.8.2")
    api(platform("androidx.compose:compose-bom:2023.08.00"))
    api("androidx.compose.ui:ui")
    api("androidx.compose.ui:ui-graphics")
    api("androidx.compose.ui:ui-tooling-preview")
    api("androidx.compose.material3:material3")
    api("androidx.appcompat:appcompat:1.6.1")
    api("com.google.android.material:material:1.11.0")
    api("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
    api("androidx.compose.runtime:runtime-livedata:1.5.4")
    api("androidx.constraintlayout:constraintlayout:2.1.4")

    val exoplayerVersion = "1.1.0"
    //    Exoplayer
    api("androidx.media3:media3-exoplayer:$exoplayerVersion")
    api("androidx.media3:media3-exoplayer-dash:$exoplayerVersion")
    api("androidx.media3:media3-ui:$exoplayerVersion")
    api("androidx.media3:media3-exoplayer-hls:$exoplayerVersion")
    api("com.google.android.exoplayer:exoplayer:2.14.0")
    api("com.google.android.exoplayer:exoplayer-ui:2.14.0")

    api("com.squareup.okhttp3:okhttp:3.12.0")
    api("com.squareup.retrofit2:retrofit:2.9.0")
    api("com.squareup.retrofit2:converter-gson:2.9.0")
    api("com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1")
    api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")

    // Glide
    api("com.github.bumptech.glide:glide:4.15.1")
    annotationProcessor("com.github.bumptech.glide:compiler:4.15.1")

    // Coil - Image
    api("io.coil-kt:coil-compose:2.2.2")
    api("io.coil-kt:coil-svg:2.2.2")
    api("io.coil-kt:coil-gif:2.2.2")

    //runtime compose
    api("androidx.lifecycle:lifecycle-runtime-compose:2.7.0")

    //Pubnub
    api("com.pubnub:pubnub-gson:4.27.0")

    api("com.google.android.material:material:1.9.0")
    

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

}