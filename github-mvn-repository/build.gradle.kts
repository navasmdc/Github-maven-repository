import com.jfrog.bintray.gradle.BintrayExtension

plugins {
    `kotlin-dsl`
    `maven-publish`
    id("com.jfrog.bintray")
}


/**
 * Fix for "Gradle 4.10.2 can't resolve dependency kotlin-scripting-compiler-embeddable"
 * (With Kotlin 1.2.60, the Kotlin Gradle Plugin driving the kotlin compiler. Now it requires extra dependencies that
 * aren't required by Gradle Kotlin DSL scripts alone and aren't embedded into Gradle)
 */
repositories {
    jcenter()
}

dependencies {
    compile(kotlin("stdlib"))
    implementation("digital.wup:android-maven-publish:3.6.2")

    testImplementation(Dependencies.junit)
    testImplementation(Dependencies.mockitoKotlin)
    testImplementation(Dependencies.mockitoInline)
}

gradlePlugin {
    plugins {
        register("com.gc.github-publisher") {
            id = "com.gc.github-publisher"
            implementationClass = "com.gc.githubmvnrepository.GithubPublisherPlugin"
        }
        register("com.gc.github-repository") {
            id = "com.gc.github-repository"
            implementationClass = "com.gc.githubmvnrepository.GithubRepositoryPlugin"
        }
    }
}

group = "com.gc"
version = "1.0.0"

val artifactoryUser: String by project
val artifactoryPassword: String by project

bintray {
    user = artifactoryUser
    key = artifactoryPassword
    pkg.apply {
        repo = "maven"
        name = "Github-mvn-repository"
        userOrg = "navasmdc"
        githubRepo = "navasmdc/Github-maven-repository"
        githubReleaseNotesFile = "README.md"
        vcsUrl = "https://github.com/navasmdc/Github-maven-repository.git"
        setLicenses("Apache-2.0")
    }
}
