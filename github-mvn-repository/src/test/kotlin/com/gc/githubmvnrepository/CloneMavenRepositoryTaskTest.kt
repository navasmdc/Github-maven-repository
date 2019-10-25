package com.gc.githubmvnrepository

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test

class CloneMavenRepositoryTaskTest {

    private val workDirectory = "/workDIrectory"
    private val commandExecutor: CommandExecutor = mock()
    private val repositoryExtension = GithubMavenRepositoryExtension(
        token = "123",
        githubUser = "user",
        repository = "mvn-repo"
    )

    @Test
    fun `taskAction clone the repo if the repository doesnt exist`() {
        val cloneMavenRepositoryTask = CloneMavenRepositoryTask.Action(
            workDirectory,
            repositoryExtension,
            commandExecutor,
            pathChecker = { false }
        )

        cloneMavenRepositoryTask()

        verify(commandExecutor).exec(
            "cd $workDirectory",
            "git clone https://${repositoryExtension.token}@github.com/${repositoryExtension.githubUser}/${repositoryExtension.repository}.git"
        )
    }

    @Test
    fun `taskAction update the repo if the repository exists`() {
        val cloneMavenRepositoryTask = CloneMavenRepositoryTask.Action(
            workDirectory,
            repositoryExtension,
            commandExecutor,
            pathChecker = { true }
        )

        cloneMavenRepositoryTask()

        verify(commandExecutor).exec(
            "cd $workDirectory/${repositoryExtension.repository}",
            "git pull"
        )
    }

    @Test(expected = IllegalStateException::class)
    fun `taskAction thrown an exception if repository is not set`() {
        repositoryExtension.repository = null
        val cloneMavenRepositoryTask = CloneMavenRepositoryTask.Action(
            workDirectory,
            repositoryExtension,
            commandExecutor,
            pathChecker = { true }
        )

        cloneMavenRepositoryTask()
    }

    @Test(expected = IllegalStateException::class)
    fun `taskAction thrown an exception if githubUser is not set`() {
        repositoryExtension.githubUser = null
        val cloneMavenRepositoryTask = CloneMavenRepositoryTask.Action(
            workDirectory,
            repositoryExtension,
            commandExecutor,
            pathChecker = { false }
        )

        cloneMavenRepositoryTask()
    }

    @Test(expected = IllegalStateException::class)
    fun `taskAction thrown an exception if token is not set`() {
        repositoryExtension.token = null
        val cloneMavenRepositoryTask = CloneMavenRepositoryTask.Action(
            workDirectory,
            repositoryExtension,
            commandExecutor,
            pathChecker = { false }
        )

        cloneMavenRepositoryTask()
    }


}