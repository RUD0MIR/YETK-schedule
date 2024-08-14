plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin)
    alias(libs.plugins.devtools.ksp)
}

android {
    namespace = "com.yetk.for_student"
    compileSdk = 34

    defaultConfig {
        minSdk = 33

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.2"
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
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.appcompat)
    implementation (libs.coroutines.core)
    implementation (libs.coroutines.android)

    implementation(projects.model)
    implementation(projects.ui)
    implementation(projects.designsystem)

    // Compose
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.graphics)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.material3)
    implementation (libs.lifecycle.runtime.compose)

    androidTestImplementation( platform(libs.compose.bom))
    androidTestImplementation(libs.compose.ui.test.junit4)
    debugImplementation(libs.compose.ui.test.manifest)


    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.espresso.core)

    //Dagger - Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    //Hilt Navigation Compose
    implementation(libs.hilt.navigation.compose)

    // Room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    //Compose navigation
    implementation(libs.compose.navigation)

    //Pager
    implementation(libs.accompanist.pager)
    implementation(libs.accompanist.pager.indicators)

    //Swipe actions
    implementation(libs.swipe)

    //Reveal swipe
    implementation(libs.revealswipe)

    //firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.firestore)

    implementation(libs.tracing.ktx)
}






