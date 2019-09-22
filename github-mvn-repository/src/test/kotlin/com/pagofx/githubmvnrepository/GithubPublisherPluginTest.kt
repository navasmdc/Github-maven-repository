package com.pagofx.githubmvnrepository

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionContainer
import org.gradle.api.tasks.TaskContainer
import org.junit.Test

class GithubPublisherPluginTest {
    @Test
    fun `apply the plugin add the extension and tasks`() {
        val extensionContainer: ExtensionContainer = mock()
        val tasksContainer: TaskContainer = mock()
        val project: Project = mock {
            on { tasks } doReturn tasksContainer
            on { extensions } doReturn extensionContainer
        }

        GithubPublisherPlugin().apply(project)

        verify(extensionContainer).create("githubMavenRepository", GithubMavenRepositoryExtension::class.java)
    }
}