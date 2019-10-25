
plugins {
    `kotlin-dsl`
    `maven-publish`

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
