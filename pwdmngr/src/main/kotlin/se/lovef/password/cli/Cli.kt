package se.lovef.password.cli

import se.lovef.password.DataNumber
import se.lovef.password.SaltGeneratorImpl
import se.lovef.password.formatter.AlphaNumericFormatter
import se.lovef.password.formatter.ReadableFormatter
import se.lovef.password.formatter.SentenceFormatter
import se.lovef.password.hasher.PasswordHasher
import kotlin.system.exitProcess


private const val helpText = """
usage: pwdmngr [OPTIONS]

Generates passwords in various formats and lengths
    
    -h, --help  Display help text
"""

fun main(args: Array<String>) {
    if ("-h" in args || "--help" in args) {
        println(helpText.trimIndent())
        exitProcess(0)
    }
    val salt = SaltGeneratorImpl.createSalt(1024).toString(Charsets.UTF_8)
    val input = input()
    for (size in 10..20 step 2) {
        println("--- $size bytes ---")
        val hash = PasswordHasher(size * 8).hash(salt, input)
        val data = DataNumber(hash)
        listOf(
            AlphaNumericFormatter(),
            ReadableFormatter(),
            SentenceFormatter()
        ).forEach {
            val formatted = it.format(data).replace(' ', '_')
            println(formatted + " (${formatted.length}, ${it.configuration})")
        }
    }
}

private fun input(): String {
    val timeBefore = System.currentTimeMillis()
    println("Input random chars for extra entropy")
    val timeAfter = System.currentTimeMillis()
    val input = System.console().readPassword().toString()
    return input + timeBefore + timeAfter
}
