package se.lovef.password.gradle

import java.io.File

interface SaltFileFactory {
    fun get(passwordIdentifier: String, path: String): SaltFile
}

class SaltFileFactoryImpl(private val projectDir: File) : SaltFileFactory {

    override fun get(passwordIdentifier: String, path: String): SaltFileImpl {
        val directory = File(path
            .replace(".", projectDir.absolutePath)
            .replace("~", File(System.getProperty("se.lovef.user.home") ?: System.getProperty("user.home")).absolutePath))
        return SaltFileImpl(passwordIdentifier, directory)
    }
}
