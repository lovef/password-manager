package se.lovef.password.gradle

import se.lovef.password.PasswordCompiler
import se.lovef.password.formatter.DataNumberFormatter
import se.lovef.password.hasher.Hasher

interface PasswordManager {
    fun get(passwordIdentifier: String): String
    fun saltDirectory(path: String): PasswordManager
}

class PasswordManagerImpl(
    private val saltFileFactory: SaltFileFactory,
    private val hasher: Hasher,
    private val formatter: DataNumberFormatter,
    private val passwordReader: PasswordReader
) : PasswordManager {

    private val saltDirectories = ArrayList<String>()

    override fun saltDirectory(path: String) = apply {
        saltDirectories += path
    }

    override fun get(passwordIdentifier: String): String {
        val compiler = PasswordCompiler(hasher, formatter)

        saltDirectories.ifEmpty { listOf(".") }
            .forEach { compiler.salt(saltFileFactory.get(passwordIdentifier, it).salt) }

        return compiler
            .password(passwordReader.get(passwordIdentifier))
            .compile()
    }
}
