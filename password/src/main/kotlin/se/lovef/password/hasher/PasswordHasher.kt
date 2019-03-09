package se.lovef.password.hasher

import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

class PasswordHasher(
    private val hashSize: Int = 256,
    private val iterations: Int = 0x1_0000
) : Hasher {

    override fun hash(salt: String, data: String): ByteArray {
        val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
        val spec = PBEKeySpec(data.toCharArray(), salt.toByteArray(), iterations, hashSize)
        return factory.generateSecret(spec).encoded
    }
}

interface Hasher {
    fun hash(salt: String, data: String): ByteArray
}
