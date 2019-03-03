package se.lovef.password

import java.util.*
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

object Password {

    fun createBase64Hash(salt: String, data: String): String {
        val bytes = createHash(salt, data)
        return Base64.getEncoder().encodeToString(bytes)
    }

    fun createPrettyHash(salt: String, data: String) =
        createHash(salt, data).toDataNumber().toStringOfChars(Letters.alphaNumeric)

    fun createReadableHash(salt: String, data: String) =
        createHash(salt, data).toDataNumber().toStringOfChars(Letters.readableAlphaNumeric)

    fun createHash(salt: String, data: String): ByteArray {
        val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
        val spec = PBEKeySpec(data.toCharArray(), salt.toByteArray(), 0x1_0000, 256)
        return factory.generateSecret(spec).encoded
    }
}
