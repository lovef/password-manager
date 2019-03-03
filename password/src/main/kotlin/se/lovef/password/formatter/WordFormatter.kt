package se.lovef.password.formatter

import se.lovef.password.DataNumber
import se.lovef.password.plusAssign

class WordFormatter : DataNumberFormatter {

    override fun format(dataNumber: DataNumber): String {
        val length = dataNumber.next(7) + 2
        var vowel = dataNumber.nextBoolean()
        val vowelIterator = dataNumber.iterator(vowels)
        val consonantIterator = dataNumber.iterator(consonants)
        val builder = StringBuilder()
        while (builder.length < length) {
            builder += if (vowel) {
                vowelIterator
            } else {
                consonantIterator
            }.next()
            vowel = !vowel
        }
        return builder.toString()
    }

    companion object {
        const val vowels = "AEIOU"
        const val consonants = "BCDFGHJKLMNPQRSTVWXZY"
    }
}
