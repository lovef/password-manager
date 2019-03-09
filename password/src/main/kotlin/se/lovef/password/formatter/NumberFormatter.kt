package se.lovef.password.formatter

import se.lovef.password.DataNumber
import se.lovef.password.plusAssign

class NumberFormatter : DataNumberFormatter {

    override fun format(dataNumber: DataNumber): String {
        val length = dataNumber.next(3) + 1
        val digitIterator = dataNumber.iterator(digits)
        if (length == 1) {
            return digitIterator.next().toString()
        }
        val builder = StringBuilder()
        builder += positiveDigits[dataNumber.next(positiveDigits.length)]
        while (builder.length < length) {
            builder += digitIterator.next()
        }
        return builder.toString()
    }

    companion object {
        const val digits = "0123456789"
        const val positiveDigits = "123456789"
    }
}
