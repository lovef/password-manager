package se.lovef.password.formatter

import se.lovef.password.DataNumber

interface DataNumberFormatter {
    fun format(dataNumber: DataNumber): String
}

interface DataNumberDecoder {
    fun decode(encoded: String): DataNumber
}
