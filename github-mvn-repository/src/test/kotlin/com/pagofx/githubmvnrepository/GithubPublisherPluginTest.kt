package com.pagofx.githubmvnrepository

import com.nhaarman.mockitokotlin2.*
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.internal.project.DefaultProject
import org.gradle.api.plugins.ExtensionContainer
import org.gradle.api.tasks.TaskContainer
import org.junit.Test

class GithubPublisherPluginTest {

//    @Test
//    fun `apply the plugin add the extension and configure the project after the evaluation`() {
//        val extensionContainer: ExtensionContainer = mock()
//        val tasksContainer: TaskContainer = mock()
//        val project: Project = mock {
//            on { tasks } doReturn tasksContainer
//            on { extensions } doReturn extensionContainer
//        }
//
//        GithubPublisherPlugin().apply(project)
//        verify(extensionContainer).create("githubMavenRepository", GithubMavenRepositoryExtension::class.java)
//        verify(tasksContainer).create("cloneMavenRepository", CloneMavenRepositoryTask::class.java)
//        verify(tasksContainer).create("pushMavenRepository", PushMavenRepositoryTask::class.java)
//        verify(project).task(eq("publishToGithubRepository"), any<Action<Task>>())
//    }

    @Test
    fun `configurePublishTask configure the project to publish in a local folder`() {
        val project: Project = DefaultProject("project",null,null,null,)

        verify(project).publishing(check{
            val publishExtension = it.invoke(it)
        })

    }
}