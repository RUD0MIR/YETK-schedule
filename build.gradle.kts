// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        google()
    }

    dependencies {
        classpath(libs.gradle)
        classpath(libs.kotlin.gradle.plugin)
        classpath(libs.hilt.android.gradle.plugin)
    }
}


plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin) apply false
    alias(libs.plugins.devtools.ksp)
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.google.services) apply false
}