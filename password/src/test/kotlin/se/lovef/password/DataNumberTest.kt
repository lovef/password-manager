package se.lovef.password

import org.junit.Rule
import org.junit.Test
import org.junit.rules.Timeout
import se.lovef.assert.v1.shouldEqual
import se.lovef.assert.v1.throws
import java.util.concurrent.TimeUnit

private val originalNumber = randomBigInt()

class DataNumberTest {

    @get:Rule var timeout = Timeout(1000, TimeUnit.MILLISECONDS)

    private val number = DataNumber(originalNumber)


    @Test fun `can read value`() {
        number.value shouldEqual originalNumber
    }

    @Test fun `can init with byte array`() {
        DataNumber.fromBytes(0x0F).toHex() shouldEqual "F"
        DataNumber.fromBytes(0x0F).toHex() shouldEqual "F"
        DataNumber.fromBytes(0x1F).toHex() shouldEqual "1F"
        DataNumber.fromBytes(-1).toHex() shouldEqual "FF"
        DataNumber.fromBytes(-1, 0x37, 0x0F).toHex() shouldEqual "FF370F"
        DataNumber.fromBytes(15).toDec() shouldEqual "15"
        DataNumber.fromBytes(0b1010).toBin() shouldEqual "1010"
        DataNumber.fromBytes(0).toHex() shouldEqual "0"
    }

    @Test fun `init data is not modified`() {
        val bytes = randomBytes()
        val initBytes = bytes.clone()
        DataNumber(initBytes)

        bytes shouldEqual initBytes
    }

    @Test fun `can convert to number of specific digits`() {
        number.let { it.toHex() shouldEqual it.toStringOfChars("01234_6789ABCDEF").replace('_', '5') }
        number.let { it.toDec() shouldEqual it.toStringOfChars("01234_6789").replace('_', '5') }
        number.let { it.toBin() shouldEqual it.toStringOfChars("0_").replace('_', '1') }
        ; { number.toStringOfChars("1") } throws Exception::class
        ; { number.toStringOfChars("") } throws Exception::class
    }

    @Test fun `leading zeroes are preserved when converting to string of chars`() {
        DataNumber.fromBytes(0b0000_1010).toStringOfChars("01") shouldEqual "00001010"
        DataNumber.fromBytes(0b0000_0000, 0b0100_1001).toStringOfChars("01") shouldEqual "0000000001001001"
    }

    @Test fun `can take a hex number`() {
        val number = DataNumber(0x1234)
        (1..5).map { number.next(16) to number.value.toInt() } shouldEqual listOf(
            4 to 0x123,
            3 to 0x12,
            2 to 0x1,
            1 to 0x0,
            0 to 0x0)
    }

    @Test fun `can iterate hex numbers`() {
        DataNumber(0x1234).list(16) shouldEqual listOf(4, 3, 2, 1)
    }

    @Test fun `can take a binary number`() {
        val number = DataNumber(0b1011)
        (1..5).map { number.next(2) to number.value.toInt() } shouldEqual listOf(
            1 to 0b101,
            1 to 0b10,
            0 to 0b1,
            1 to 0b0,
            0 to 0b0)
    }

    @Test fun `can take a boolean from number`() {
        val number = DataNumber(0b1011)
        (1..5).map { number.nextBoolean() to number.value.toInt() } shouldEqual listOf(
            true to 0b101,
            true to 0b10,
            false to 0b1,
            true to 0b0,
            false to 0b0)
    }

    @Test fun `data is proportional to number of bytes`() {
        (1..5).forEach { bytes ->
            (5..10).forEach { radix ->
                DataNumber(zeroedBytes(bytes)).list(radix) sizeShouldEqual DataNumber(maxedBytes(bytes)).list(radix)
            }
        }
    }

    private infix fun List<Int>.sizeShouldEqual(other: List<Int>) {
        proveThat("size should equal:\n", this, '\n', other) {
            size shouldEqual other.size
        }
    }

    private fun maxedBytes(length: Int) = ByteArray(length)
        .also { bytes -> (0 until bytes.size).forEach { bytes[it] = -1 } }

    private fun zeroedBytes(length: Int) = ByteArray(length)
        .also { bytes -> (0 until bytes.size).forEach { bytes[it] = 0 } }

    private fun DataNumber.toHex() = toString(16).toUpperCase()
    private fun DataNumber.toDec() = toString(10).toUpperCase()
    private fun DataNumber.toBin() = toString(2).toUpperCase()
}
