package se.lovef.password.formatter

import se.lovef.password.DataNumber
import se.lovef.password.Letters

class ReadableFormatter : DataNumberFormatter() {

    override val configuration = "readable"

    override fun format(dataNumber: DataNumber) =
        dataNumber.toStringOfChars(Letters.readableAlphaNumeric)
}
