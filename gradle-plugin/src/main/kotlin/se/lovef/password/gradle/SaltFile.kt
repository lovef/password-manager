package se.lovef.password.gradle

import se.lovef.password.SaltGenerator
import se.lovef.password.SaltGeneratorImpl
import se.lovef.password.toBase64
import java.io.File
import java.util.*

interface SaltFile {
    val salt: String
}

class SaltFileImpl(
    passwordIdentifier: String,
    private val directory: File,
    private val saltGenerator: SaltGenerator = SaltGeneratorImpl
) : SaltFile {

    val file = File(directory, "$passwordIdentifier.properties")

    override val salt by lazy {
        readSaltFromFile() ?: createSalt().also { persist(it) }
    }

    private fun readSaltFromFile() = file.properties()?.getProperty("salt")

    private fun createSalt() = saltGenerator.createSalt(1024).toBase64()

    private fun persist(salt: String) {
        Properties().also {
            it["salt"] = salt
            println("Created salt file: $file")
            directory.mkdirs()
            file.createNewFile()
            it.store(file.writer(Charsets.UTF_8), "Generated salt file")
        }
    }

    override fun toString() = "SaltFileImpl($file, $saltGenerator)"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SaltFileImpl

        if (saltGenerator != other.saltGenerator) return false
        if (file != other.file) return false

        return true
    }

    override fun hashCode(): Int {
        var result = saltGenerator.hashCode()
        result = 31 * result + file.hashCode()
        return result
    }
}

private fun File.properties(): Properties? {
    if (!isFile) {
        return null
    }
    return Properties().also { it.load(inputStream()) }
}
