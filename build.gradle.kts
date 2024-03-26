// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        mavenCentral()
    }

    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.50")                  // Hilt
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.7")       // Jetpack Navigation
    }
}

plugins {
    id("com.android.application") version "8.3.0" apply false
    id("org.jetbrains.kotlin.android") version "1.8.0" apply false
}