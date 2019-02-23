package se.lovef.test

import se.lovef.assert.v1.shouldBeTrue
import se.lovef.password.DataNumber
import java.io.File
import java.math.BigInteger
import java.util.*
import java.util.concurrent.ThreadLocalRandom

val random get() = ThreadLocalRandom.current()

private val bytes: ByteArray = randomBytes()
val randomBytes get() = bytes.clone()
private val dataNumber = DataNumber(randomBytes)
val randomDataNumber get() = DataNumber(dataNumber)

fun randomBigInt() = BigInteger(1, randomBytes())
fun randomDataNumber() = DataNumber(randomBytes())

fun randomBytes() = randomBytes(10, 100)
fun randomBytes(length: Int) = randomBytes(length, length + 1)
fun randomBytes(from: Int, to: Int) = ByteArray(random.nextInt(from, to))
    .also { random.nextBytes(it) }

fun ByteArray.toBase64() = Base64.getEncoder().encodeToString(this)

inline fun proveThat(vararg messages: Any, proof: () -> Unit) {
    try {
        proof()
    } catch (throwable: Throwable) {
        val message = messages
            .map { if (it is Function0<*>) it.invoke() else it }
            .joinToString(" ") { it.toString() }
        throw AssertionError(message, throwable)
    }
}

inline infix fun <T> Iterable<T>.shouldAll(assertion: (T) -> Unit) {
    var firstError: Throwable? = null
    val failed = ArrayList<T>()
    forEach {
        try {
            assertion.invoke(it)
        } catch (t: Throwable) {
            if (firstError == null) {
                firstError = t
            }
            failed += it
        }
    }
    if (firstError != null) {
        val failedString = failed.take(10).joinToString()
        val failedMessage = if (failed.size > 10) ", first 10: $failedString" else ": $failedString"
        throw AssertionError("Failed for ${failed.size} elements" + failedMessage, firstError)
    }
}

infix fun String.shouldMatchEntire(regex: Regex) {
    try {
        matches(regex).shouldBeTrue()
    } catch (e: Throwable) {
        val found = regex.findAll(this)
        val firstFew = found.take(3).joinToString { it.value }
        throw java.lang.AssertionError("$regex should match the entire string\n\"${this}\"\nFound $firstFew...")
    }
}


fun File.dir(path: String) = File(this, path)

fun File.properties(path: String): Map<String, String> {
    if (!this.isDirectory) {
        throw Exception("$this is not a directory")
    }
    val file = File(this, path)
    return file.properties
}

val File.properties: Map<String, String>
    get() {
        if (!this.isFile) {
            throw Exception("$this is not a file")
        }
        this.isFile
        return Properties().also { it.load(this.inputStream()) }
            .entries
            .map { "${it.key}" to "${it.value}" }
            .toMap()
    }
