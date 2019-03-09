package se.lovef.password.hasher

import org.junit.Test
import se.lovef.assert.v1.shouldEqual
import se.lovef.assert.v1.shouldNotEqual
import se.lovef.password.shouldAll
import se.lovef.password.toBase64

private const val exampleSalt = "" +
        "qfTujPRHfCgzAqbo6Ai6aBWCMtqBM9Qr89hsgyWQA9ufyAwvBe2xshhT4VEgiNnUBdHGMLZVGmQ6seQhrdjVnozGZ7uUEEZJNYHq" +
        "KWEM27bbAyUj7VJ6zFrrSAnZE9tiQgbyWr6sxdr3Hr9Fegw3PTYiCXyewUbZpsJkuS5fKqYiofERxDPpTJXa9A4NWesusi6uUMet" +
        "DKEyQV9uZg2KCNSV832Whege866xcpfqqHrDrD3utL83ev44QnMZJrAQV2gnfJd3VfH7zEm6CdGDmzaB9X9gSawcMmKBLz35Zfaz" +
        "Ty2Yp3WakaDkrUxWJp3Zh5XdvxLsC6VSUKWw5yTkAbfF8yZS7hTyceReDE97BTLVYhBnqzh6JdHmqJY42ZwJbm4xmxWqoeKwkmKu" +
        "zggDfnpkUAHxVN5sMuVvJHg2VoZjEYUh8TvzeTpJzLZD2C3uCVdLSqEh6vM27A4m8gprGMyTnD8E6B4hvF7jVdcaxSqti9nhcY4J" +
        "3ZaJYDBQHXX3xdGaFLuUrXZkM4hzKT9B59WT95oewAH6FoephYowYKWVu8do9pRKEYkQegwSMrictdXcDkp4nNFPBavPUFrjFtgK" +
        "67WHB9vD9JaFUNGEinSCyTabn7T4mH8pMuBquWpTMDZnSTdKYcpVfdRbKFaiyZ7WQekeEnyoM6Sjv7oB3wh8edixbUjZ7GkUzzxm" +
        "uLbQtQYfSWzKfx3segE8EP7EtJSx6QkuegYnf6DUJvKNc4um5cpqyowwDbiVXtw8qSwQdfuRyps36BkmUiGosgEn4T4uKXNEu74H" +
        "KCWcatEYfkeCGDttyKkkSEMFY75rHfiiJejTMmAxfEsvkfdSBfuTHzhZoYPBDEAJnLuqbFQipeA9UZK7jMf8bmx2R9NCdt9w7jQC" +
        "n56xXP9e3HWn9Yqz66kb6pKX8gcR5rmB8TswX7ZiJr5aUSdLQNioxeXKmsgcnuWDaMT5bjYbEg3VmXDJvehyMmQvccQ7NcAMTEAQ"

private const val exampleData = "data to hash"

class PasswordHasherTest {

    private var passwordHasher = PasswordHasher()

    @Test fun `create hash`() {
        val hashWithExampleSalt = "gmr5AYx/SbAVYHshrPJj1ILm55PohJyS4ZZYBI4ZfB0="
        hash64(exampleSalt, exampleData) shouldEqual hashWithExampleSalt
        hash64(exampleSalt, "other data") shouldNotEqual hashWithExampleSalt
        hash64("other salt", exampleData) shouldNotEqual hashWithExampleSalt
    }

    @Test fun `create hash of different lengths`() {
        listOf(10, 100, 1000) shouldAll {
            passwordHasher = PasswordHasher(hashSize = it * 8)
            hash(exampleSalt, exampleData).size shouldEqual it
        }
    }

    @Test fun `create hash of with different number of iterations`() {
        val hashes = listOf(10, 100, 1000).map {
            passwordHasher = PasswordHasher(iterations = it)
            hash64(exampleSalt, exampleData)
        }
        hashes shouldEqual hashes.toSet().toList()
    }

    private fun hash64(salt: String, data: String) = hash(salt, data).toBase64()
    private fun hash(salt: String, data: String) = passwordHasher.hash(salt, data)
}
