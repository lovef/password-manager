package se.lovef.password

import org.junit.Test
import se.lovef.assert.v1.shouldContain
import se.lovef.assert.v1.shouldMatch
import se.lovef.assert.v1.shouldNotContain

class SaltGeneratorTest {

    @Test fun `create salt`() {
        val largeSalt = SaltGeneratorImpl.createSalt(10000)
        Letters.alphaNumeric.forEach {
            largeSalt shouldContain it
        }
        largeSalt shouldMatch "^[a-zA-Z0-9]+$"
        largeSalt shouldMatch "[a-z]+"
        largeSalt shouldMatch "[A-Z]+"
        largeSalt shouldMatch "[0-9]+"
    }

    @Test fun `create readable salt`() {
        val largeSalt = SaltGeneratorImpl.createReadableSalt(10000)
        Letters.readableAlphaNumeric.forEach {
            largeSalt shouldContain it
        }
        Letters.ambiguousChars.forEach {
            largeSalt shouldNotContain it
        }
        largeSalt shouldMatch "^[a-zA-Z0-9]+$"
        largeSalt shouldMatch "[a-z]+"
        largeSalt shouldMatch "[A-Z]+"
        largeSalt shouldMatch "[0-9]+"
    }
}
