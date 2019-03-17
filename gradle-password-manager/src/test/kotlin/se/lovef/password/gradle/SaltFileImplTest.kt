package se.lovef.password.gradle

import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import se.lovef.assert.v1.shouldBeFalse
import se.lovef.assert.v1.shouldEqual
import se.lovef.assert.v1.shouldNotEqual
import se.lovef.assert.v1.throws
import se.lovef.password.SaltGenerator
import se.lovef.password.parseBase64
import se.lovef.password.toBase64
import java.io.File
import java.util.*
import kotlin.random.Random

class SaltFileImplTest {

    @get:Rule val testProjectDir = TemporaryFolder()
    private val dir by lazy {
        testProjectDir.create()
        File(testProjectDir.root, "dir/subdir")
            .also { it.exists().shouldBeFalse() }
    }
    private val passwordIdentifier = "pwd-" + Random.nextInt()
    private val saltFile by lazy { SaltFileImpl(passwordIdentifier, dir, TestSaltGenerator()) }
    private val file = File(dir, "$passwordIdentifier.properties")

    @Test fun `test salt can only be generated once`() {
        TestSaltGenerator().also {
            it.createSalt(1024)
            ; { it.createSalt(1024) } throws Exception::class
        }
    }

    @Test fun `salt file quality`() {
        SaltFileImpl("abc", File(".")) shouldEqual SaltFileImpl("abc", File("."))
        SaltFileImpl("abc", File(".")) shouldNotEqual SaltFileImpl("other", File("."))
        SaltFileImpl("abc", File(".")) shouldNotEqual SaltFileImpl("abc", File("other"))
        SaltFileImpl("abc", File(".")) shouldNotEqual SaltFileImpl("abc", File("."), TestSaltGenerator())
    }

    @Test fun `salt file is not created before it is used`() {
        saltFile.file.path shouldEqual file.path
        saltFile.file.exists().shouldBeFalse()
        dir.exists().shouldBeFalse()
    }

    @Test fun `salt file is generated and persisted when requesting salt`() {
        saltFile.salt.parseBase64() shouldEqual defaultSalt
        saltFile.file.properties()?.get("salt") shouldEqual defaultSalt.toBase64()
    }

    @Test fun `salt is read from file if it exists`() {
        val testSalt = createSaltFile()
        saltFile.salt shouldEqual testSalt
        saltFile.file.properties()?.get("salt") shouldEqual testSalt
    }

    private fun createSaltFile(): String {
        dir.mkdirs()
        file.createNewFile()
        val testSalt = "some salt " + Random.nextInt()
        Properties().also {
            it["salt"] = testSalt
            it.store(file.outputStream(), "Test salt file")
        }
        return testSalt
    }
}

private fun File.properties(): Properties? {
    if (!isFile) {
        return null
    }
    return Properties().also { it.load(inputStream()) }
}

private val defaultSalt = TestSaltGenerator().createSalt(1024)

private class TestSaltGenerator : SaltGenerator {
    private var created = false
    override fun createSalt(size: Int) = if (!created) {
        created = true
        "generatedSalt($size)".toByteArray()
    } else throw Exception("Salt should only be created once")
}
