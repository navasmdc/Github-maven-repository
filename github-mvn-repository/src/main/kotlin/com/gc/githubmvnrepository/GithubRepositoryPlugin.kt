package com.gc.githubmvnrepository

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.credentials.HttpHeaderCredentials
import org.gradle.authentication.http.HttpHeaderAuthentication
import org.gradle.internal.impldep.com.google.common.annotations.VisibleForTesting
import org.gradle.kotlin.dsl.maven
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.repositories

class GithubRepositoryPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            extensions.create("githubMavenRepository", GithubMavenRepositoryExtension::class.java)
            afterEvaluate(::configureGithubRepository)
        }
    }

    @VisibleForTesting
    fun configureGithubRepository(project: Project) = with(project) {
        repositories {
            with(githubMavenRepository) {
                maven("https://raw.githubusercontent.com/$githubUser/$repository/master") {
                    name = "GithubRepository"
                    credentials(HttpHeaderCredentials::class.java) {
                        name = "Authorization"
                        value = "Bearer $token"
                    }
                    authentication {
                        register("header", HttpHeaderAuthentication::class)
                    }
                }
            }
        }
        subprojects.forEach(::configureGithubRepository)
    }
}

