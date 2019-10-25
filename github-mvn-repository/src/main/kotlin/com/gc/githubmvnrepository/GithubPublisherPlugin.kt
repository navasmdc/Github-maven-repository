package com.gc.githubmvnrepository

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.internal.impldep.com.google.common.annotations.VisibleForTesting
import java.lang.IllegalStateException

class GithubPublisherPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            extensions.create("githubMavenRepository", GithubMavenRepositoryExtension::class.java)
            afterEvaluate {
                configurePublishTask(this)
                configureCustomTasks(this)
            }
        }
    }

    @VisibleForTesting
    fun configurePublishTask(project: Project) = with (project) {
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

    @VisibleForTesting
    fun configureCustomTasks(project: Project) = with(project) {
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
