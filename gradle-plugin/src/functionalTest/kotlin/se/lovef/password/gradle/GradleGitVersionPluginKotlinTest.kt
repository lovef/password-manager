@file:Suppress("FunctionName")

package se.lovef.password.gradle

import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.GradleRunner
import org.intellij.lang.annotations.Language
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import se.lovef.assert.v1.shouldContain
import se.lovef.assert.v1.shouldEqual
import se.lovef.password.parseBase64
import se.lovef.test.dir
import se.lovef.test.shouldAll
import java.io.File

class GradleGitVersionPluginKotlinTest {

    @get:Rule val testProjectDir = TemporaryFolder()
    @get:Rule val testHomeDir = TemporaryFolder()
    private val dir get() = testProjectDir.root
    private val homeDir get() = testHomeDir.root
    private val buildGradle by lazy {
        testProjectDir.newFile("build.gradle.kts")
            .also { it.appendText("""plugins { id("se.lovef.password-manager") }""") }
    }

    @Before fun before() {
        System.setProperty("se.lovef.user.home", homeDir.path)
    }

    operator fun File.plusAssign(@Language("groovy") text: String) {
        buildGradle.appendText('\n' + text.trimIndent())
    }

    private fun gradle(vararg command: String): BuildResult = GradleRunner.create()
        .withProjectDir(testProjectDir.root)
        .withArguments(*command)
        .withPluginClasspath()
        .build()

    @Test fun `get passwords with new salt files`() {
        buildGradle += """
            passwordManager {
                saltDirectories("~/pwd", "./pwd")
            }
            println("password1: " + passwordManager.get("password1") + ", " + passwordManager.get("password1"))
            println("password2: " + passwordManager.get("password2"))
        """
        val result = gradle("-Dpassword1=myPassword1", "-Dpassword2=myPassword2")

        println(result.output)

        val passwords = Passwords(
            "password1" to "myPassword1",
            "password2" to "myPassword2"
        )
        val passwordManager = dir.passwordManager(passwords)
            .saltDirectory("~/pwd")
            .saltDirectory("./pwd")
        result.output shouldContain "password1: " + passwordManager.get("password1").let { "$it, $it" }
        result.output shouldContain "password2: " + passwordManager.get("password2")
        val generatedSalts = listOf(
            homeDir.dir("pwd").readSalt("password1"),
            homeDir.dir("pwd").readSalt("password2"),
            dir.dir("pwd").readSalt("password1"),
            dir.dir("pwd").readSalt("password2")
        )
        generatedSalts shouldAll { it?.parseBase64()?.size shouldEqual 1024 }
        generatedSalts.toSet().toList() shouldEqual generatedSalts
        val createdSaltFilePattern = "Created salt file: ([^\n]+.properties)".toRegex()
        createdSaltFilePattern.findAll(result.output).map { it.groupValues[1] }.toList() shouldEqual listOf(
            homeDir.dir("pwd").dir("password1.properties"),
            dir.dir("pwd").dir("password1.properties"),
            homeDir.dir("pwd").dir("password2.properties"),
            dir.dir("pwd").dir("password2.properties")
        ).map { it.toString() }
    }
}
