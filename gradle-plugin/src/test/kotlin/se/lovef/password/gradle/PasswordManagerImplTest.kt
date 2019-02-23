package se.lovef.password.gradle

import org.junit.Test
import se.lovef.assert.v1.shouldEqual
import se.lovef.password.DataNumber
import se.lovef.password.PasswordCompiler
import se.lovef.password.formatter.DataNumberFormatter
import se.lovef.password.hasher.Hasher

class PasswordManagerImplTest {

    private val passwordManagerImpl = PasswordManagerImpl(saltFileFactory, hasher, formatter, passwordReader)

    @Test fun `get password`() {
        val passwordIdentifier = "pwd"
        passwordManagerImpl.get(passwordIdentifier) shouldEqual PasswordCompiler(hasher, formatter)
            .salt(saltFileFactory.get(passwordIdentifier, ".").salt)
            .password(passwordReader.get(passwordIdentifier))
            .compile()
    }

    @Test fun `get password with configured salt file locations`() {
        passwordManagerImpl
            .saltDirectory("a")
            .saltDirectory("b")

        val passwordIdentifier = "pwd"
        passwordManagerImpl.get(passwordIdentifier) shouldEqual PasswordCompiler(hasher, formatter)
            .salt(saltFileFactory.get(passwordIdentifier, "a").salt)
            .salt(saltFileFactory.get(passwordIdentifier, "b").salt)
            .password(passwordReader.get(passwordIdentifier))
            .compile()
    }

    @Test fun `get password is deterministic`() {
        val passwordIdentifier = "pwd"
        passwordManagerImpl.get(passwordIdentifier) shouldEqual passwordManagerImpl.get(passwordIdentifier)
    }

    @Test fun `get password with configured salt file locations is deterministic`() {
        passwordManagerImpl
            .saltDirectory("a")
            .saltDirectory("b")

        val passwordIdentifier = "pwd"
        passwordManagerImpl.get(passwordIdentifier) shouldEqual passwordManagerImpl.get(passwordIdentifier)
    }
}

private data class TestSaltFile(override val salt: String) : se.lovef.password.gradle.SaltFile

private val saltFileFactory = object : SaltFileFactory {
    override fun get(passwordIdentifier: String, path: String) =
        TestSaltFile("$passwordIdentifier@$path")
}

private val hasher = object : Hasher() {
    override val configuration = "hasher"
    override fun hash(salt: String, data: String) = (salt + data).toByteArray()
}

private val formatter = object : DataNumberFormatter() {
    override val configuration = "formatter"
    override fun format(dataNumber: DataNumber) = dataNumber.toString()
}

private val passwordReader = object : PasswordReader {
    override fun get(passwordIdentifier: String) = "$passwordIdentifier-pwd"
}
