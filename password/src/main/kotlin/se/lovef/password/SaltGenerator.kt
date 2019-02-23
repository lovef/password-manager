package se.lovef.password

import java.security.SecureRandom

interface SaltGenerator {
    fun createSalt(size: Int = 16): String?
    fun createReadableSalt(size: Int = 16): String?
}

object SaltGeneratorImpl : SaltGenerator {

    private val random = SecureRandom()

    override fun createSalt(size: Int): String? {
        return createSalt(size, Letters.alphaNumeric)
    }

    override fun createReadableSalt(size: Int): String? {
        return createSalt(size, Letters.readableAlphaNumeric)
    }

    private fun createSalt(size: Int, chars: String): String {
        return (1..size)
            .map { chars[random.nextInt(chars.length)] }
            .joinToString(separator = "")
    }
}
