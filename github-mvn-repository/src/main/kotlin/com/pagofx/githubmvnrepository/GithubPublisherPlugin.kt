package com.pagofx.githubmvnrepository

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.maven.MavenPublication
import java.lang.IllegalStateException

class GithubPublisherPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            extensions.create("githubMavenRepository", GithubMavenRepositoryExtension::class.java)
            afterEvaluate {
                configurePublishTask()
                val cloneMavenRepositoryTask =
                    tasks.create("cloneMavenRepository", CloneMavenRepositoryTask::class.java)
                val pushMavenRepositoryTask =
                    tasks.create("pushMavenRepository", PushMavenRepositoryTask::class.java)
                val publishMavenTask = tasks.first { it.name.contains("LocalGithub") }
                task("publishToGithubRepository") {
                        group = "publishing"
                        dependsOn.run {
                            add(cloneMavenRepositoryTask)
                            add(publishMavenTask)
                            add(pushMavenRepositoryTask)
                        }
                 }
            }
        }
    }

    private fun Project.configurePublishTask() {
        val repository = githubMavenRepository.repository
            ?: throw IllegalStateException("githubMavenRepository.repository must be set")
        plugins.apply("digital.wup.android-maven-publish")
        publishing {
            repositories {
                maven {
                    name = "LocalGithub"
                    url = uri("${rootDir.parentFile.absolutePath}/$repository")
                }
            }
            if (isAnAndroidProject()) {
                publications {
                    register("library", MavenPublication::class.java) {
                        from(components.findByName("android"))
                    }
                }
            }
        }
    }

}
