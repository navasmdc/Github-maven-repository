
plugins {
//    kotlin("jvm") version "1.3.40"
    `kotlin-dsl`
//    java
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
        register("github-publisher") {
            id = "github-publisher"
            implementationClass = "com.pagofx.githubmvnrepository.GithubPublisherPlugin"
        }
    }
}

//apply(plugin = "maven-publish")

//val sourcesJar by tasks.registering(Jar::class) {
//    classifier = "sources"
//    from(sourceSets.main.get().allSource)
//}



//publishing {
//    repositories {
//        maven {
////            name = "mvnrepository"
//            // change to point to your repo, e.g. http://my.org/repo
//            url = uri("../../mvn-repository")
//        }
//    }
//    publications {
//        register("mavenJava", MavenPublication::class) {
//            from(components["java"])
//            artifact(sourcesJar.get())
//        }
//    }
//}
group = "com.pagofx"
version = "1.0.2"

//publishing {
//    publications {
//        plug
//        plugin(MavenPublication) {
//            groupId publishGroup
//                    artifactId project.name
//                    version publishVersionName
//                    from components.java
//        }
//    }
//
//    repositories {
//        maven {
//            name 'Artifactory'
//            url publishReleaseRepositoryUrl
//                    credentials {
//                        username = project.artifactoryUser
//                        password = project.artifactoryPassword
//                    }
//        }
//    }
//}
