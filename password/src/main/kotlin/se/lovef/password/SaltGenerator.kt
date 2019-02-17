package se.lovef.password

import java.security.SecureRandom

object SaltGenerator {

    private val random = SecureRandom()

    fun createSalt(size: Int = 16): String? {
        return createSalt(size, Letters.alphaNumeric)
    }

    fun createReadableSalt(size: Int = 16): String? {
        return createSalt(size, Letters.readableAlphaNumeric)
    }

    private fun createSalt(size: Int, chars: String): String {
        return (1..size)
            .map { chars[random.nextInt(chars.length)] }
            .joinToString(separator = "")
    }
}
