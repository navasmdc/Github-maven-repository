package com.pagofx.githubmvnrepository

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.lang.IllegalStateException

open class PushMavenRepositoryTask  : DefaultTask() {

    override fun getGroup() = "publishing"

    private val action = with(project) {
        Action(
            githubMavenRepository.repository ?: throw IllegalStateException("githubMavenRepository.repository must be set"),
            name,
            version.toString(),
            rootDir.parentFile.absolutePath
        )
    }

    @TaskAction
    fun pushMavenRepository() = action()

    internal class Action(
        private val repository: String,
        private val artifactName: String,
        private val artifactVersion: String,
        private val workDirectory: String,
        private val commandExecutor: CommandExecutor = CommandExecutor(workDirectory)
    ) {
        operator fun invoke() = commandExecutor.exec(
            "cd $workDirectory/$repository",
            "git add .",
            "git commit -m \"Release for $artifactName:$artifactVersion\"",
            "git push"
        )
    }
}
