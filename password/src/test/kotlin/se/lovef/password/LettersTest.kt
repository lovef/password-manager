package se.lovef.password

import org.junit.Test
import se.lovef.assert.v1.shouldEqual

class LettersTest {

    @Test fun `string chars`() {
        Letters.alphaNumeric shouldEqualAsStrings (('0'..'9') + ('a'..'z') + ('A'..'Z'))
        Letters.ambiguousChars shouldEqual "0O1Il"
        Letters.readableAlphaNumeric shouldEqualAsStrings Letters.alphaNumeric.toList() - Letters.ambiguousChars.toList()
    }

    private infix fun String.shouldEqualAsStrings(other: Iterable<Char>) {
        this shouldEqual other.joinToString(separator = "")
    }
}
