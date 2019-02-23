package se.lovef.password

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.mock
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import se.lovef.assert.v1.shouldEqual
import java.util.*

class SaltFileGeneratorTest {

    @get:Rule var testFolder = TemporaryFolder()

    private val saltGenerator: SaltGenerator = mock {
        on { createReadableSalt(any()) }.doAnswer { "readableSalt(${it.arguments.toList()})" }
    }
    private val saltFileGenerator = SaltFileGenerator(saltGenerator)

    @Test fun `generate default salt file generator`() {
        saltFileGenerator.generate(testFolder.root.path, "test-salt.properties")
        val files = testFolder.root.listFiles()
        files.map { it.name } shouldEqual listOf("test-salt.properties")
        files.first().readText()
        Properties().also { it.load(files.first().inputStream()) }.toMap() shouldEqual mapOf(
            "length" to "1024",
            "salt" to saltGenerator.createReadableSalt(1024)
        )
    }
}
