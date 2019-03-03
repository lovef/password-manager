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

private val wordPattern by lazy {
    val v = "[${WordFormatter.vowels}]"
    val c = "[${WordFormatter.consonants}]"
    "^(($v$c)+|($c$v)+|$c($v$c)+|$v($c$v)+)$".toRegex()
}

class WordFormatterTest {

    @get:Rule var timeout = Timeout(1000, TimeUnit.MILLISECONDS)

    @Test fun `a word should be of proper length`() {
        val lengths = words.map { it.length }.toSet()
        proveThat(words,
            "have proper lengths:",
            { lengths.toSortedSet() }) {
            lengths.min() shouldEqual 2
            lengths.max() shouldEqual 8
        }
    }

    @Test fun `a word should have alternating vowels and consonants`() {
        proveThat(words, "have alternating vowels and consonants") {
            words shouldAll { it shouldMatch wordPattern }
        }
    }

    @Test fun `the same data yields the same words`() {
        val data = DataNumber.fromBytes(-0x3f, -0x33, -0x52, 0x51, 0x7b, 0x7a, -0x2c, 0xb, -0x8, 0x3e, -0x27, -0x51,
            -0x40, -0x7a, 0x2e, -0x2a, 0x37, -0x24, 0x1e, -0x53, 0x4f, -0x6e, -0x75, 0x6f, -0x77, 0xd, 0x54, 0x76)
        val string = data.formatWords().joinToString()
        string shouldEqual "MI, ULAHUNO, QIXEJ, AV, JOFEGETI, ONEKOZA, TER, VANETEM, UKUTOCA, CIGINU, CABABA"
    }
}

private val wordFormatter = WordFormatter()
private val words = (0..100).map { randomDataNumber().formatWords() }.flatten()

private fun DataNumber.formatWords(): ArrayList<String> {
    val list = ArrayList<String>()
    while (hasNext())
        list += formatWord()
    return list
}

private fun DataNumber.formatWord() = wordFormatter.format(this)
