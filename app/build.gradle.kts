import org.gradle.api.JavaVersion.VERSION_11
import org.gradle.api.JavaVersion.VERSION_17
import org.gradle.kotlin.dsl.android
import org.gradle.kotlin.dsl.hilt

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.example.mazaadytask"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.mazaadytask"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        buildConfig = true
        dataBinding = true
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
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(project(":domain"))
    implementation(project(":core"))
    implementation(project(":di"))
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    coreLibraryDesugaring (libs.desugar.jdk.libs)

    //hilt
    implementation(libs.hilt.android)
    implementation(libs.hilt.compiler)
    ksp(libs.hilt.compiler)


}