package se.lovef.password

import java.security.SecureRandom

interface SaltGenerator {
    fun createSalt(size: Int = 16): ByteArray
}

object SaltGeneratorImpl : SaltGenerator {

    private val secureRandom = SecureRandom()

    override fun createSalt(size: Int): ByteArray {
        return ByteArray(size)
            .also { secureRandom.nextBytes(it) }
    }

    override fun toString(): String {
        return "SaltGeneratorImpl"
    }
}
