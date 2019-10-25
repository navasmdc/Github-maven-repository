package com.gc.githubmvnrepository

import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.api.publish.PublishingExtension

fun Project.githubMavenRepository(configure: GithubMavenRepositoryExtension.() -> Unit): Unit =
    (this as ExtensionAware).extensions.configure("githubMavenRepository", configure)

val Project.githubMavenRepository : GithubMavenRepositoryExtension get() =
    (this as ExtensionAware).extensions.getByName("githubMavenRepository") as GithubMavenRepositoryExtension

internal fun Project.publishing(lambda: PublishingExtension.() -> Unit) =
    (extensions.getByName("publishing") as PublishingExtension).apply(lambda)

internal fun Project.isAnAndroidProject() = extensions.findByName("android") != null