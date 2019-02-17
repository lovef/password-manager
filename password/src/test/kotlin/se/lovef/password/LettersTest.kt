package se.lovef.password

import org.junit.Test
import se.lovef.assert.v1.shouldEqual
import se.lovef.assert.v1.throws

class LettersTest {

    @Test fun `string chars`() {
        Letters.alphaNumeric shouldEqualAsStrings (('0'..'9') + ('a'..'z') + ('A'..'Z'))
        Letters.ambiguousChars shouldEqual "0O1Il"
        Letters.readableAlphaNumeric shouldEqualAsStrings Letters.alphaNumeric.toList() - Letters.ambiguousChars.toList()
    }

    private infix fun String.shouldEqualAsStrings(other: Iterable<Char>) {
        this shouldEqual other.joinToString(separator = "")
    }

    @Test fun `convert byte array to string representing number of arbitrary base`() {
        byteArrayOf(0x0F).toStringOfChars("0123456789ABCDEF") shouldEqual "F"
        byteArrayOf(0x1F).toStringOfChars("0123456789ABCDEF") shouldEqual "1F"
        byteArrayOf(-1).toStringOfChars("0123456789ABCDEF") shouldEqual "FF"
        byteArrayOf(-1, 0x37, 0x0F).toStringOfChars("0123456789ABCDEF") shouldEqual "FF370F"
        byteArrayOf(15).toStringOfChars("0123456789") shouldEqual "15"
        byteArrayOf(0b1010).toStringOfChars("01") shouldEqual "1010"
        byteArrayOf(0).toStringOfChars("01") shouldEqual "0"
        { byteArrayOf(0).toStringOfChars("") } throws Exception::class
    }
}
