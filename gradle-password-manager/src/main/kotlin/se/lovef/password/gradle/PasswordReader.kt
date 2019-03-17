package se.lovef.password.gradle


import java.awt.FlowLayout
import java.util.*
import javax.swing.*


interface PasswordReader {
    fun get(passwordIdentifier: String): String
}

class PasswordReaderImpl : PasswordReader {

    private val cache = HashMap<String, String>()

    override fun get(passwordIdentifier: String) = cache[passwordIdentifier]
        ?: requestPassword(passwordIdentifier).also { cache[passwordIdentifier] = it }

    private fun requestPassword(passwordIdentifier: String) = System.getProperty(passwordIdentifier)
        .printlnIfNull("Could not read $passwordIdentifier from system properties")
        ?: readFromConsole(passwordIdentifier)
            .printlnIfNull("Could not read $passwordIdentifier from the console")
        ?: readFromGui(passwordIdentifier)

    private fun readFromConsole(passwordIdentifier: String): String? {
        val console = System.console() ?: return null
        return console.readPassword("\nPassword for '$passwordIdentifier': ").toString()
    }

    private fun readFromGui(passwordIdentifier: String): String {
        println("Requesting GUI input from You...")
        return PasswordPanel.requestPassword(passwordIdentifier)
    }
}

fun <T : Any?> T.printlnIfNull(message: String) = apply {
    if (this == null) {
        println(message)
    }
}

/** Based on https://stackoverflow.com/a/8881370/1020871 */
private class PasswordPanel : JPanel(FlowLayout()) {

    private val passwordField = JPasswordField(20)
    private var entered = false

    val enteredPassword
        get() = if (entered) String(passwordField.password) else ""

    init {
        add(JLabel("Password: "))
        add(passwordField)
        passwordField.setActionCommand("OK")
        passwordField.addActionListener {
            if (it.actionCommand == "OK") {
                entered = true

                // https://stackoverflow.com/a/51356151/1020871
                SwingUtilities.getWindowAncestor(it.source as JComponent)
                    .dispose()
            }
        }
    }

    private fun request(passwordIdentifier: String) = apply {
        JOptionPane.showOptionDialog(null, this@PasswordPanel,
            "Enter $passwordIdentifier",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.INFORMATION_MESSAGE,
            null, emptyArray(), null)
    }

    companion object {

        fun requestPassword(passwordIdentifier: String) = PasswordPanel()
            .request(passwordIdentifier)
            .enteredPassword
    }
}
