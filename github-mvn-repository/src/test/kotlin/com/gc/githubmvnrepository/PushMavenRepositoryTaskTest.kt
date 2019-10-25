package com.gc.githubmvnrepository

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test

class PushMavenRepositoryTaskTest {

    @Test
    fun `pushMavenRepository commit the last release version and push it to the repository`() {
        val commandExecutor: CommandExecutor = mock()
        val pushMavenRepositoryTask = PushMavenRepositoryTask.Action(
            repository = "mvn-repo",
            artifactName = "library",
            artifactVersion = "1.0.0",
            workDirectory = "/workdirectory",
            commandExecutor = commandExecutor
        )

        pushMavenRepositoryTask()

        verify(commandExecutor).exec(
            "cd /workdirectory/mvn-repo",
            "git add .",
            "git commit -m \"Release for library:1.0.0\"",
            "git push"
        )
    }
}