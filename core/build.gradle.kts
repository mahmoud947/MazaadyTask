import org.gradle.api.JavaVersion.VERSION_11
import org.gradle.api.JavaVersion.VERSION_17
import org.gradle.api.JavaVersion.VERSION_1_8
import org.gradle.kotlin.dsl.libs

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.example.core"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildFeatures {
        buildConfig = true
        dataBinding = true
        viewBinding = true
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            buildConfigField("String", "API_PRIVATE_KEY", "\"${project.findProperty("API_PRIVATE_KEY")}\"")
            buildConfigField("String", "BASE_URL", "\"${project.findProperty("BASE_URL")}\"")

        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "API_PRIVATE_KEY", "\"${project.findProperty("API_PRIVATE_KEY")}\"")
            buildConfigField("String", "BASE_URL", "\"${project.findProperty("BASE_URL")}\"")
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = VERSION_17
        targetCompatibility = VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    packaging {
        resources {
            excludes += "META-INF/gradle/incremental.annotation.processors"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    coreLibraryDesugaring (libs.desugar.jdk.libs)

    // ViewModel
    implementation(libs.lifecycle.viewmodel)
    // LiveData
    implementation(libs.lifecycle.livedata)
    implementation(libs.lifecycle.extensions)

    //retrofit
    implementation(libs.retrofit)
    //converter
    implementation( libs.converter.gson)
    //gson
    implementation(libs.gson)
    //logging
    implementation(libs.logging.interceptor)
    //okhttp
    implementation(libs.okhttp)
    // Glide
    implementation(libs.glide)
    annotationProcessor(libs.glide.compiler)


    //hilt
    implementation(libs.hilt.android)
    implementation(libs.hilt.compiler)
    ksp(libs.hilt.compiler)

    implementation(libs.androidx.swiperefreshlayout.v120alpha01)

    implementation (libs.lottie)


}