package se.lovef.password

import se.lovef.password.formatter.DataNumberFormatter
import se.lovef.password.hasher.Hasher

class PasswordCompiler(
    private val hasher: Hasher,
    private val formatter: DataNumberFormatter
) {

    private val salts = ArrayList<String>()
    private val passwords = ArrayList<String>()

    private var sorted = false
    private var withoutDuplicates = false

    fun salt(salt: String) = apply { salts.add(salt) }

    fun password(password: String) = apply { passwords.add(password) }

    fun sorted() = apply { sorted = true }

    fun withoutDuplicates() = apply { withoutDuplicates = true }

    fun compile() = formatter.format(hasher.hash(salts.compile(), passwords.compile()).toDataNumber())

    private fun ArrayList<String>.compile(): String {
        return let { if (sorted) it.sorted() else it }
            .let { if (withoutDuplicates) it.toSet() else it }
            .joinToString(separator = "\n")
    }
}
