package se.lovef.password

import org.junit.Test
import se.lovef.assert.v1.shouldBeGreaterOrEqualTo
import se.lovef.assert.v1.shouldEqual

class SaltGeneratorTest {

    @Test fun `create salt`() {
        val size = 10000
        val salt = SaltGeneratorImpl.createSalt(size)
        salt.size shouldEqual size
        salt.toSet().size shouldBeGreaterOrEqualTo 250
    }
}
