package se.lovef.password.formatter

import org.junit.Test
import se.lovef.assert.v1.shouldEqual
import se.lovef.assert.v1.shouldNotContain
import se.lovef.password.*


private val readableFormatter = ReadableFormatter()
private val example = readableFormatter.format(randomDataNumber)
private val pattern = "([0-9a-zA-Z])+".toRegex()

class ReadableFormatterTest {

    @Test fun `should have correct format`() {
        example shouldMatchEntire pattern
        Letters.ambiguousChars.asIterable() shouldAll { example shouldNotContain it }
    }

    @Test fun `the same data yields the same format`() {
        val data = DataNumber.fromBase64("lc2uUXt61Av4PtmvwIYu1jfcHq1PkotviQ1Udg==")
        val string = readableFormatter.format(data)
        string shouldEqual "4Z65RZFHw2XvX3c9BC8Vhwv4sHqB6W76ChVRdp6"
    }
}

