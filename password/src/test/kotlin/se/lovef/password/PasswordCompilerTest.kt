package se.lovef.password

import org.junit.Test
import se.lovef.assert.v1.shouldEqual
import se.lovef.assert.v1.shouldNotEqual

class PasswordCompilerTest {

    @Test fun `compile password hash of salt and password`() {
        compiler()
            .salt("salt")
            .password("password")
            .hash() shouldEqual hash("salt", "password")
    }

    @Test fun `compile password hash of multiple components`() {
        compiler()
            .salt("salt 1")
            .salt("salt 2")
            .password("password 1")
            .password("password 2")
            .hash() shouldEqual hash("salt 1\nsalt 2", "password 1\npassword 2")
    }

    @Test fun `salt order matters`() {
        val (a, b) = (1..2).map { compiler().pwd() }
        a.salt_1().salt_2() hashShouldNotEqual b.salt_2().salt_1()
    }

    @Test fun `password order matters`() {
        val (a, b) = (1..2).map { compiler().salt() }
        a.pwd_1().pwd_2() hashShouldNotEqual b.pwd_2().pwd_1()
    }

    @Test fun `salt and password order matters not with applied sorting`() {
        val (a, b) = (1..2).map { compiler() }
        a.pwd_2().pwd_1().salt_2().salt_1().sorted() hashShouldEqual b.pwd_1().pwd_2().salt_1().salt_2()
    }


    @Test fun `repeated salt matters`() {
        val (a, b) = (1..2).map { compiler().pwd() }
        a.salt() hashShouldNotEqual b.salt().salt()
    }

    @Test fun `repeated password matters`() {
        val (a, b) = (1..2).map { compiler().salt() }
        a.pwd() hashShouldNotEqual b.pwd().pwd()
    }

    @Test fun `repeated salt or passwords matters not with removed duplicates`() {
        val (a, b) = (1..2).map { compiler() }
        a.pwd().pwd().salt().salt().withoutDuplicates() hashShouldEqual b.pwd().salt()
    }
}

private infix fun PasswordCompiler.hashShouldEqual(other: PasswordCompiler) {
    hash() shouldEqual other.hash()
}

private infix fun PasswordCompiler.hashShouldNotEqual(other: PasswordCompiler) {
    hash() shouldNotEqual other.hash()
}

private fun compiler() = PasswordCompiler()
private fun PasswordCompiler.pwd() = password("password")
private fun PasswordCompiler.pwd_1() = password("password 1")
private fun PasswordCompiler.pwd_2() = password("password 2")
private fun PasswordCompiler.salt() = salt("salt")
private fun PasswordCompiler.salt_1() = salt("salt 1")
private fun PasswordCompiler.salt_2() = salt("salt 2")

private fun hash(salt: String, password: String) = Password.createHash(salt, password)
