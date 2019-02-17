package se.lovef.password

import java.math.BigInteger

object Letters {

    const val alphaNumeric = "0123456789" +
            "abcdefghijklmnopqrstuvwxyz" +
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    const val ambiguousChars = "0O" + "1Il"
    const val readableAlphaNumeric = "23456789" +
            "abcdefghijkmnopqrstuvwxyz" +
            "ABCDEFGHJKLMNPQRSTUVWXYZ"
}

fun ByteArray.toStringOfChars(chars: String): String {
    var number = BigInteger(1, this)
    val radix = BigInteger.valueOf(chars.length.toLong())
    val returnNumberBackwards = StringBuilder()
    do {
        returnNumberBackwards.append(chars[number.mod(radix).toInt()])
        number = number.divide(radix)
    } while (number > BigInteger.ZERO)
    return returnNumberBackwards.reverse().toString()
}
