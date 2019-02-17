package se.lovef.password

import org.junit.Test
import se.lovef.assert.v1.shouldEqual
import se.lovef.assert.v1.shouldNotEqual

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

class PasswordTest {

    @Test fun `create hash`() {
        val hashWithExampleSalt = byteArrayOf(-126, 106, -7, 1, -116, 127, 73, -80, 21, 96, 123, 33, -84,
            -14, 99, -44, -126, -26, -25, -109, -24, -124, -100, -110, -31, -106, 88, 4, -114, 25, 124, 29)
        Password.createHash(exampleSalt, "data to hash") shouldEqual hashWithExampleSalt
        Password.createHash("other salt", "data to hash") shouldNotEqual hashWithExampleSalt
    }

    @Test fun `create Base64 hash`() {
        val hashWithExampleSalt = "gmr5AYx/SbAVYHshrPJj1ILm55PohJyS4ZZYBI4ZfB0="
        Password.createBase64Hash(exampleSalt, "data to hash") shouldEqual hashWithExampleSalt
        Password.createBase64Hash("other salt", "data to hash") shouldNotEqual hashWithExampleSalt
    }

    @Test fun `create pretty hash`() {
        val hashWithExampleSalt = "uVpTDapDX6dcDDj1iTCJazHpVgsSi4ok0844OFhhCzz"
        Password.createPrettyHash(exampleSalt, "data to hash") shouldEqual hashWithExampleSalt
        Password.createPrettyHash("other salt", "data to hash") shouldNotEqual hashWithExampleSalt
    }

    @Test fun `create readable hash`() {
        val hashWithExampleSalt = "kxU4AFAtxg8YeweLVhc7REqDPrgbEiGPFHnzLqb8Cga2"
        Password.createReadableHash(exampleSalt, "data to hash") shouldEqual hashWithExampleSalt
        Password.createReadableHash("other salt", "data to hash") shouldNotEqual hashWithExampleSalt
    }
}
