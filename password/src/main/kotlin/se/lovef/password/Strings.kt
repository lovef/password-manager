package se.lovef.password

import java.util.*

operator fun StringBuilder.plusAssign(char: Char) {
    append(char)
}

operator fun StringBuilder.plusAssign(string: String) {
    append(string)
}

fun ByteArray.toBase64(): String = Base64.getEncoder().encodeToString(this)
fun String.parseBase64(): ByteArray = Base64.getDecoder().decode(this)
