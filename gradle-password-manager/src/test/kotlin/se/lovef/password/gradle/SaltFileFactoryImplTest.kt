package se.lovef.password.gradle

import org.junit.Test
import se.lovef.assert.v1.shouldEqual
import java.io.File

class SaltFileFactoryImplTest {

    private val passwordIdentifier = "pwd"
    private val projectDir = File("projectDir")
    private val absoluteProjectDir = projectDir.absoluteFile
    private val absoluteHomeDir = File(System.getProperty("user.home")).absoluteFile

    private val saltFileFactoryImpl = SaltFileFactoryImpl(projectDir = projectDir)

    @Test fun `get salt file relative to project dir`() {
        get(".") shouldEqual SaltFileImpl(passwordIdentifier, absoluteProjectDir)
        get("sub") shouldEqual SaltFileImpl(passwordIdentifier, File(absoluteProjectDir, "sub"))
        get("./sub") shouldEqual SaltFileImpl(passwordIdentifier, File(absoluteProjectDir, "sub"))
        get("sub.dir") shouldEqual SaltFileImpl(passwordIdentifier, File(absoluteProjectDir, "sub.dir"))
    }

    @Test fun `get salt file relative to home dir`() {
        get("~") shouldEqual SaltFileImpl(passwordIdentifier, absoluteHomeDir)
        get("~/sub") shouldEqual SaltFileImpl(passwordIdentifier, File(absoluteHomeDir, "sub"))
    }

    @Test fun `get salt file relative to fake home dir`() {
        val fakeHome = "fakeHome"
        System.setProperty("se.lovef.user.home", fakeHome)
        val absoluteFakeHomeDir = File(fakeHome).absoluteFile
        get("~") shouldEqual SaltFileImpl(passwordIdentifier, absoluteFakeHomeDir)
        get("~/sub") shouldEqual SaltFileImpl(passwordIdentifier, File(absoluteFakeHomeDir, "sub"))
    }

    private fun get(dir: String) = saltFileFactoryImpl.get(passwordIdentifier, dir)
}

