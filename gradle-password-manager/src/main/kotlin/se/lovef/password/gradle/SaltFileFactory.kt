package se.lovef.password.gradle

import java.io.File

interface SaltFileFactory {
    fun get(passwordIdentifier: String, path: String): SaltFile
}

class SaltFileFactoryImpl(private val projectDir: File) : SaltFileFactory {

    override fun get(passwordIdentifier: String, path: String): SaltFileImpl {
        val home = File(System.getProperty("se.lovef.user.home") ?: System.getProperty("user.home")).absolutePath
        val resolvedPath = path
            .replaceFirstChar('~', home)
            .replaceFirstChar('.', projectDir.absolutePath)
            .let { if(File(it).isAbsolute) it else File(projectDir, it).path }
        val directory = File(resolvedPath)
        return SaltFileImpl(passwordIdentifier, directory.absoluteFile)
    }

    private fun String.replaceFirstChar(char: Char, string: String): String {
        if (this.startsWith(char)) {
            return this.replaceRange(0, 1, string)
        }
        return this
    }
}
