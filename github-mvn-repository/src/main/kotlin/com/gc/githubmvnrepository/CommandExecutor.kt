package com.gc.githubmvnrepository

import java.io.File

class CommandExecutor(private val workDirectory: String) {
    internal fun exec(vararg commands: String) {
        File("$workDirectory/commands.sh").run {
            writeText(
                """
                    #!/usr/bin/env sh
                    ${commands.joinToString("\n")}
                    rm $absolutePath"""
            )
            Runtime.getRuntime().exec("sh $absolutePath").waitFor()
        }
    }
}
