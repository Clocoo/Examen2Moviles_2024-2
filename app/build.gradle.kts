plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.0" // O la versión correspondiente
    id ("kotlin-kapt")
}

android {
    namespace = "casa.osmar.projectofinalmoviles"
    compileSdk = 35

    defaultConfig {
        applicationId = "casa.osmar.projectofinalmoviles"
        minSdk = 33
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.runtime.livedata)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Dependencias extras
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")  // Retrofit
    implementation ("com.squareup.retrofit2:converter-moshi:2.9.0") // Moshi Converter
    implementation ("com.squareup.moshi:moshi:1.13.0") // Moshi
    implementation ("com.squareup.moshi:moshi-kotlin:1.13.0") // Moshi Kotlin Adapter
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.0")
    implementation ("androidx.compose.foundation:foundation-layout:1.7.5") // Esta es la librería que contiene LazyVerticalGrid
    implementation ("io.coil-kt:coil-compose:2.3.0") // Para imágenes de internet

    // Librerias aparte para el Serializable
    implementation ("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")

    // Navegación
    implementation("androidx.navigation:navigation-fragment-ktx:2.8.4")
    implementation("androidx.navigation:navigation-ui-ktx:2.8.4")
    implementation ("androidx.navigation:navigation-compose:2.8.4")

    // Database con Room
    implementation ("androidx.room:room-runtime:2.6.1")
    kapt ("androidx.room:room-compiler:2.6.1")
    implementation ("androidx.room:room-ktx:2.6.1")
}