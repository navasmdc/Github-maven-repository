package com.gc.githubmvnrepository

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.io.File

open class CloneMavenRepositoryTask : DefaultTask() {
    
    override fun getGroup() = "publishing"

    private val workDirectory = project.rootDir.parentFile.absolutePath

    private val action = Action(
        workDirectory,
        project.githubMavenRepository,
        CommandExecutor(workDirectory)
    )

    @TaskAction
    fun cloneRepo() = action()

    internal class Action(
        private val workDirectory: String,
        private val mavenRepository: GithubMavenRepositoryExtension,
        private val commandExecutor: CommandExecutor = CommandExecutor(workDirectory),
        private val pathChecker: (String) -> Boolean = { path -> File(path).exists() }
    ) {
        operator fun invoke() {
            val repository = mavenRepository.repository ?: throw IllegalStateException("githubMavenRepository.repository must be set")
            if (pathChecker("$workDirectory/$repository")) updateRepo(repository)
            else cloneRepository(repository)
        }

        private fun updateRepo(repository: String) = commandExecutor.exec(
                "cd $workDirectory/$repository",
                "git pull"
            )

        private fun cloneRepository(repository : String) {
            val githubUser = mavenRepository.githubUser ?: throw IllegalStateException("githubMavenRepository.githubUser must be set")
            val token = mavenRepository.token ?: throw IllegalStateException("githubMavenRepository.token must be set")
            commandExecutor.exec(
                "cd $workDirectory",
                "git clone https://$token@github.com/$githubUser/$repository.git"
            )
        }
    }
}
