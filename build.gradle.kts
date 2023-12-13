// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    val agp_version by extra("7.4.2")
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(ClassPaths.ANDROID_GRADLE)
        classpath(ClassPaths.KOTLIN_GRADLE)
        classpath(ClassPaths.HILT_GRADLE)
        classpath(ClassPaths.GOOGLE_SERVICES)
        classpath("com.android.tools.build:gradle:$agp_version")
    }
}

plugins {
    //id(Plugins.SPOTLESS) version Versions.SPOTLESS
    //id(Plugins.DETEKT) version Versions.DETEKT
}

allprojects {
    repositories {
        google()
        mavenCentral()
        jitPack()
    }
}