package se.lovef.password.formatter

import se.lovef.password.DataNumber
import se.lovef.password.plusAssign

class SentenceFormatter(
    private val wordFormatter: DataNumberFormatter = WordFormatter(),
    private val digitFormatter: DataNumberFormatter = NumberFormatter()
) : DataNumberFormatter() {

    override val configuration = "sentence"

    override fun format(dataNumber: DataNumber): String {
        val builder = StringBuilder()
        builder.append(next(dataNumber))
        while (dataNumber.hasNext()) {
            builder += ' ' + next(dataNumber)
        }
        return builder.toString()
    }

    private fun next(dataNumber: DataNumber) = when (dataNumber.next(4)) {
        0 -> wordFormatter.format(dataNumber)
        1 -> wordFormatter.format(dataNumber).toLowerCase()
        2 -> wordFormatter.format(dataNumber).toLowerCase().capitalize()
        else -> digitFormatter.format(dataNumber)
    }
}
