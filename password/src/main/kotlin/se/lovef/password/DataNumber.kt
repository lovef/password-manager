package se.lovef.password

import java.math.BigInteger

class DataNumber {

    var value: BigInteger
        private set
    private var maxValue: BigInteger

    constructor(bigInteger: BigInteger) {
        value = bigInteger
        maxValue = bigInteger
    }

    constructor(value: Long) : this(BigInteger.valueOf(value))
    constructor(bytes: ByteArray) {
        val clone = bytes.clone()
        value = BigInteger(1, clone)
        clone[0] = -1
        maxValue = BigInteger(1, clone)
    }

    constructor(data: DataNumber) {
        value = data.value
        maxValue = data.maxValue
    }

    fun toStringOfChars(chars: String): String {
        if (chars.length < 2) {
            throw Exception("Must have at least two chars")
        }
        val data = DataNumber(this)
        val radix = BigInteger.valueOf(chars.length.toLong())
        val returnNumberBackwards = StringBuilder()
        do {
            returnNumberBackwards.append(chars[data.next(radix)])
        } while (data.hasNext())
        return returnNumberBackwards.reverse().toString()
    }

    fun nextBoolean() = next(radix2) == 1

    fun next(radix: Int) = next(BigInteger.valueOf(radix.toLong()))

    fun next(radix: BigInteger): Int {
        val number = value % radix
        value /= radix
        maxValue /= radix
        return number.toInt()
    }

    fun hasNext() = maxValue > BigInteger.ZERO

    fun list(radix: Int) = iterator(radix).asSequence().toList()
    fun iterator(radix: Int) = Iterator(BigInteger.valueOf(radix.toLong()))

    inner class Iterator(private val radix: BigInteger) : kotlin.collections.Iterator<Int> {
        override fun hasNext() = this@DataNumber.hasNext()
        override fun next() = next(radix)
    }

    fun list(chars: String) = iterator(chars).asSequence().toList()
    fun iterator(chars: String) = CharIterator(chars)

    inner class CharIterator(private val chars: String) : kotlin.collections.Iterator<Char> {
        private val radix = BigInteger.valueOf(chars.length.toLong())
        override fun hasNext() = this@DataNumber.hasNext()
        override fun next() = chars[next(radix)]
    }

    fun toString(radix: Int): String = value.toString(radix)

    companion object {
        fun fromBytes(vararg bytes: Byte) = DataNumber(bytes)
        private val radix2 = BigInteger.valueOf(2)
    }
}

fun ByteArray.toDataNumber() = DataNumber(this)
