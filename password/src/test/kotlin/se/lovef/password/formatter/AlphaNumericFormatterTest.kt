package se.lovef.password.formatter

import org.junit.Ignore
import org.junit.Test
import se.lovef.assert.v1.shouldEqual
import se.lovef.password.DataNumber
import se.lovef.password.randomDataNumber
import se.lovef.password.shouldMatchEntire


private val alphaNumericFormatter = AlphaNumericFormatter()
private val example = alphaNumericFormatter.format(randomDataNumber)
private val pattern = "([0-9a-zA-Z])+".toRegex()

class AlphaNumericFormatterTest {

    @Test fun `should have correct format`() {
        example shouldMatchEntire pattern
    }

    @Test fun `the same data yields the same format`() {
        val data = DataNumber.fromBase64("lc2uUXt61Av4PtmvwIYu1jfcHq1PkotviQ1Udg==")
        val string = alphaNumericFormatter.format(data)
        string shouldEqual "7zMJdI31StX7omoggFdxG55e1YB5okCMsMIad8"
    }

    @Test @Ignore fun `formatted data can be decoded`() {
        alphaNumericFormatter.decode(example) shouldEqual randomDataNumber
    }
}

