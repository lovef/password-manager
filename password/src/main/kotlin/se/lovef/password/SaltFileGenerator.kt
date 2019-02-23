package se.lovef.password

import java.io.File
import java.util.*

class SaltFileGenerator(private val saltGenerator: SaltGenerator = SaltGeneratorImpl) {

    fun generate(dir: String, fileName: String) {
        val properties = Properties()
        mapOf(
            "length" to 1024,
            "salt" to saltGenerator.createReadableSalt(1024)
        ).forEach { key, value -> properties[key] = "$value" }
        val file = File(dir, fileName)
        file.createNewFile()
        properties.store(file.outputStream(), "Salt")
    }
}
