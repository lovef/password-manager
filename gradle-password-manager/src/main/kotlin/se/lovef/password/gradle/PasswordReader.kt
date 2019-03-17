package se.lovef.password.gradle

interface PasswordReader {
    fun get(passwordIdentifier: String): String
}

class PasswordReaderImpl : PasswordReader {
    override fun get(passwordIdentifier: String) = System.getProperty(passwordIdentifier)
        ?: TODO("Need to read password from user input")
}
