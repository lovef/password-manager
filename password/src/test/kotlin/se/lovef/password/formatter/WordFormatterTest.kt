package se.lovef.password.formatter

import org.junit.Rule
import org.junit.Test
import org.junit.rules.Timeout
import se.lovef.assert.v1.shouldEqual
import se.lovef.password.*
import java.util.*
import java.util.concurrent.TimeUnit

private val wordPattern by lazy {
    val v = "[${WordFormatter.vowels}]"
    val c = "[${WordFormatter.consonants}]"
    "(($v$c)+|($c$v)+|$c($v$c)+|$v($c$v)+)".toRegex()
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
            words shouldAll { it shouldMatchEntire wordPattern }
        }
    }

    @Test fun `the same data yields the same words`() {
        val data = DataNumber.fromBase64("wc2uUXt61Av4PtmvwIYu1jfcHq1PkotviQ1Udg==")
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
