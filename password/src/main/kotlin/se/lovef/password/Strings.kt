package se.lovef.password

operator fun StringBuilder.plusAssign(char: Char) {
    append(char)
}

operator fun StringBuilder.plusAssign(string: String) {
    append(string)
}
