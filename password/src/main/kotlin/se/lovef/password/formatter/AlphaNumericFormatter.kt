package se.lovef.password.formatter

import se.lovef.password.DataNumber
import se.lovef.password.Letters

class AlphaNumericFormatter : DataNumberFormatter() {

    override val configuration = "alhphanumeric"

    override fun format(dataNumber: DataNumber) =
        dataNumber.toStringOfChars(Letters.alphaNumeric)
}
