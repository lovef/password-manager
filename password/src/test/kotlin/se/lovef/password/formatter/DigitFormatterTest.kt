package se.lovef.password.formatter

import org.junit.Rule
import org.junit.Test
import org.junit.rules.Timeout
import se.lovef.assert.v1.shouldEqual
import se.lovef.assert.v1.shouldMatch
import se.lovef.password.DataNumber
import se.lovef.password.proveThat
import se.lovef.password.randomDataNumber
import se.lovef.password.shouldAll
import java.util.concurrent.TimeUnit

private val digitPattern by lazy {
    "^([0-9]|[1-9][0-9]+)$".toRegex()
}

class DigitFormatterTest {

    @get:Rule var timeout = Timeout(1000, TimeUnit.MILLISECONDS)

    @Test fun `a digit should be of proper length`() {
        val lengths = digits.map { it.length }.toSet()
        proveThat(digits,
            "have proper lengths:",
            { lengths.toSortedSet() }) {
            lengths.min() shouldEqual 1
            lengths.max() shouldEqual 3
        }
    }

    @Test fun `a number should have correct format`() {
        proveThat(digits, "have correct format") {
            digits shouldAll { it shouldMatch digitPattern }
        }
    }

    @Test fun `the same data yields the same digits`() {
        val data = DataNumber.fromBytes(-0x6b, -0x33, -0x52, 0x51, 0x7b, 0x7a, -0x2c, 0xb, -0x8, 0x3e, -0x27, -0x51,
            -0x40, -0x7a, 0x2e, -0x2a, 0x37, -0x24, 0x1e, -0x53, 0x4f, -0x6e, -0x75, 0x6f, -0x77, 0xd, 0x54, 0x76)
        val string = (1..10).joinToString { data.formatDigit() }
        string shouldEqual "39, 322, 0, 70, 6, 408, 1, 8, 6, 92"
    }
}


private val digitFormatter = DigitFormatter()
private val digits = (0..100).map { randomDigit() }

private fun randomDigit() = randomDataNumber().formatDigit()
private fun DataNumber.formatDigit() = digitFormatter.format(this)
