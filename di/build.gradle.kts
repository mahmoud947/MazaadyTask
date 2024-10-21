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
    namespace = "com.example.di"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
    buildFeatures {
        buildConfig = true
    }
    buildTypes {
        debug {
            isMinifyEnabled = false
            buildConfigField(
                "String",
                "API_PRIVATE_KEY",
                "\"${project.findProperty("API_PRIVATE_KEY")}\""
            )
            buildConfigField("String", "BASE_URL", "\"${project.findProperty("BASE_URL")}\"")
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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
    implementation(project(":core"))
    implementation(project(":domain"))
    implementation(project(":data"))
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    coreLibraryDesugaring (libs.desugar.jdk.libs)


    //retrofit
    implementation(libs.retrofit)
    //converter
    implementation(libs.converter.gson)
    //gson
    implementation(libs.gson)
    //logging
    implementation(libs.logging.interceptor)
    //okhttp
    implementation(libs.okhttp)


    //hilt
    implementation(libs.hilt.android)
    implementation(libs.hilt.compiler)
    ksp(libs.hilt.compiler)

}