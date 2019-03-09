package se.lovef.password.formatter

import se.lovef.password.DataNumber
import se.lovef.password.Letters
import java.math.BigInteger

class AlphaNumericFormatter : DataNumberFormatter, DataNumberDecoder {
    override fun format(dataNumber: DataNumber) =
        dataNumber.toStringOfChars(Letters.alphaNumeric)

    override fun decode(encoded: String): DataNumber {
        var data = BigInteger.ZERO
        val radix = BigInteger.valueOf(Letters.alphaNumeric.length.toLong())
        val digits = Letters.alphaNumeric.mapIndexed { i, c -> c to BigInteger.valueOf(i.toLong()) }.toMap()
        encoded.forEach {
            data = data * radix + digits.getValue(it)
        }
        return DataNumber(data)
    }
}
