// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {

    val kotlinVersion = "1.3.40"

//    apply from: "gradle-scripts/dependencies.gradle"

    repositories {
        google()
        jcenter()
        mavenLocal()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:3.4.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        classpath("digital.wup:android-maven-publish:3.6.2")
//        classpath "com.gc:resourcesgenerator:$publishVersionName"
    }
}

//apply from: "$rootDir/gradle-scripts/dependencies.gradle"

allprojects {
    repositories {
        google()
        jcenter()
    }
}

task("clean",type =  Delete::class) {
    delete(rootProject.buildDir)
}
