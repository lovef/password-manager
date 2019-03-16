package se.lovef.password.formatter

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import org.junit.Rule
import org.junit.Test
import org.junit.rules.Timeout
import se.lovef.assert.v1.shouldBeGreaterOrEqualTo
import se.lovef.assert.v1.shouldEqual
import se.lovef.assert.v1.shouldNotEqual
import se.lovef.password.DataNumber
import se.lovef.test.proveThat
import se.lovef.test.randomBytes
import se.lovef.test.randomDataNumber
import java.util.concurrent.TimeUnit

class SentenceFormatterTest {

    @get:Rule var timeout = Timeout(1000, TimeUnit.MILLISECONDS)

    @Test fun `sentence should have good composition`() {
        proveThat("Sentence should have good composition:", longSentence) {
            longSentence.percentage(UPPER_CASE) shouldBeGreaterOrEqualTo 10
            longSentence.percentage(LOWER_CASE) shouldBeGreaterOrEqualTo 10
            longSentence.percentage(DIGIT) shouldBeGreaterOrEqualTo 10
            longSentence.percentage(CAPITALIZED) shouldBeGreaterOrEqualTo 10
        }
    }

    @Test fun `same data always yield the same sentence`() {
        val data = DataNumber.fromBase64("lc2uUXt61Av4PtmvwIYu1jfcHq1PkotviQ1Udg==")
        SentenceFormatter().format(data) shouldEqual "Epawihab URUWILA 335 Utamopan Upataboj AKA UBUDE Ca 36 On QOBABA"
    }

    @Test fun `different byte arrays yields different sentences`() {
        randomDataNumber.formatSentence() shouldNotEqual randomDataNumber().formatSentence()
    }
}

const val WORD = "WORD"
const val UPPER_CASE = "WORD"
const val LOWER_CASE = "word"
const val CAPITALIZED = "Word"
const val DIGIT = "DIGIT"

private val sentenceFormatter = SentenceFormatter(
    wordFormatter = mock { on { format(any()) }.doReturn(WORD) },
    digitFormatter = mock { on { format(any()) }.doReturn(DIGIT) }
)

private val longSentence = object {
    val value = DataNumber(randomBytes(100)).formatSentence()
    val parts = value.split(' ')
    val digits = parts.filter { it == DIGIT }
    val capitalizedWords = parts.filter { it == CAPITALIZED }
    val upperCaseWords = parts.filter { it == UPPER_CASE }
    val lowerCaseWords = parts.filter { it == LOWER_CASE }
    val size = parts.size

    fun percentage(type: String) = when (type) {
        UPPER_CASE -> upperCaseWords.size * 100 / size
        LOWER_CASE -> lowerCaseWords.size * 100 / size
        CAPITALIZED -> capitalizedWords.size * 100 / size
        DIGIT -> digits.size * 100 / size
        else -> throw Exception("Invalid type $type")
    }

    override fun toString() = value
}

private fun DataNumber.formatSentence() = sentenceFormatter.format(this)
