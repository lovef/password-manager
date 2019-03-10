package se.lovef.password.formatter

import se.lovef.password.Configurable
import se.lovef.password.DataNumber

abstract class DataNumberFormatter : Configurable() {
    abstract fun format(dataNumber: DataNumber): String
}
