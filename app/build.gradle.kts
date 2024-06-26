plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id ("com.google.devtools.ksp")
    id ("dagger.hilt.android.plugin")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.yetk.yetkschedule"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.yetk.yetkschedule"
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
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
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
    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.activity:activity-compose:1.7.0")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation(project(":model"))
    implementation(project(":for_student"))
    implementation(project(":designsystem"))
    implementation(project(":ui"))

    //Test & Debug
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    //Dagger - Hilt
    implementation("com.google.dagger:hilt-android:2.48")
    ksp("com.google.dagger:hilt-compiler:2.48")
    //Hilt Navigation Compose
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")

    // Room
    val roomVersion = "2.6.1"
    implementation("androidx.room:room-runtime: $roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    ksp("androidx.room:room-compiler:$roomVersion")

    //Compose navigation
    implementation("androidx.navigation:navigation-compose:2.7.5")

    //Pager
    implementation("com.google.accompanist:accompanist-pager:0.17.0")
    implementation("com.google.accompanist:accompanist-pager-indicators:0.17.0")

    //Swipe actions
    implementation("me.saket.swipe:swipe:1.2.0")

    //firebase
    implementation(platform("com.google.firebase:firebase-bom:32.7.0"))
    implementation("com.google.firebase:firebase-firestore")
}