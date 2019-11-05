# Github maven repository
This library provives two different gradle plugins to handle a maven repository created in Github.
See [this section](#how-to-create-a-maven-repository-on-github) in order to create a private maven repository in your Github Account.

## How to use

Add the plugins dependency to your project.

```groovy
buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath 'com.gc:github-mvn-repository:1.0.0'
    }
}
```

Both of plugins use the same extension to work correctly.

**Groovy**

```groovy
githubMavenRepository {
    repository "mvn-repo"
    githubUser "navasmdc"
    token "30081177e2cccb64a5b18b46166824a94748dd66"
}

```

**Kotlin**

```kotlin
githubMavenRepository {

    repository = "mvn-repo"
    githubUser = "navasmdc"
    token = "30071199e8cccb69a5b17b46166824a94748dd66"
}

```

### Github Publisher plugin

This plugin allows to publish the artifact in a maven repostory created on Github.

**Groovy**

```groovy
apply plugin: 'com.gc.github-publisher'

```

**Kotlin**

```kotlin
plugins { id(¨com.gc.github-publisher¨) }
```

To publish the artifact just execute the following command:

```bash
./gradlew publishToGithubRepository
```

### Github Repository plugin

This plugin allows to access to dependencies uploaded in your private maven repository on Github.

**Groovy**

```groovy
apply plugin: 'com.gc.github-repository'

```

**Kotlin**

```kotlin
plugins { id(¨com.gc.github-repository¨) }
```

Once your plugin is applied just add your dependencies.

### How to create a maven repository on github

In order to have a maven repository allocated in your Github account the only thing you have to do is create a private repository, for instance `mvn-repo` this name is what you are goint to set in the plugin configuration:

```
githubMavenRepository {
    repository = "mvn-repo"
}
```

Once you already have a private repository the next step is to create a token to access to this repository, for that, go to [https://github.com/settings/tokens](https://github.com/settings/tokens) and then create a new token, set all permissions to this token, set a name and it's done.

> Remember copy the value, due to when the window is closed Github dosn´t display its value again.

Once you already have the token just put it the plugin configuration:

```groovy
githubMavenRepository {
    token "30081177e2cccb64a5b18b46166824a94748dd66"
}

```

> My advice is don't keep your token value in your repository files, instead of that using a gradle property, somehing like:

```groovy
githubMavenRepository {
    token githubToken
}

```

**gradle.properties**

```
githubToken=PLACEHOLDER
```

**~/.gradle/gradle.properties**

```
githubToken=30081177e2cccb64a5b18b46166824a94748dd66
```