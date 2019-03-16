package se.lovef.password.formatter

import org.junit.Rule
import org.junit.Test
import org.junit.rules.Timeout
import se.lovef.assert.v1.shouldEqual
import se.lovef.password.DataNumber
import se.lovef.test.proveThat
import se.lovef.test.randomDataNumber
import se.lovef.test.shouldAll
import se.lovef.test.shouldMatchEntire
import java.util.concurrent.TimeUnit

private val digitPattern by lazy {
    "([0-9]|[1-9][0-9]+)".toRegex()
}

class NumberFormatterTest {

    @get:Rule var timeout = Timeout(1000, TimeUnit.MILLISECONDS)

    @Test fun `a digit should be of proper length`() {
        val lengths = numbers.map { it.length }.toSet()
        proveThat(numbers,
            "have proper lengths:",
            { lengths.toSortedSet() }) {
            lengths.min() shouldEqual 1
            lengths.max() shouldEqual 3
        }
    }

    @Test fun `a number should have correct format`() {
        proveThat(numbers, "have correct format") {
            numbers shouldAll { it shouldMatchEntire digitPattern }
        }
    }

    @Test fun `the same data yields the same digits`() {
        val data = DataNumber.fromBase64("lc2uUXt61Av4PtmvwIYu1jfcHq1PkotviQ1Udg==")
        val string = (1..10).joinToString { data.formatDigit() }
        string shouldEqual "39, 322, 0, 70, 6, 408, 1, 8, 6, 92"
    }
}


private val numberFormatter = NumberFormatter()
private val numbers = (0..100).map { randomDigit() }

private fun randomDigit() = randomDataNumber().formatDigit()
private fun DataNumber.formatDigit() = numberFormatter.format(this)
