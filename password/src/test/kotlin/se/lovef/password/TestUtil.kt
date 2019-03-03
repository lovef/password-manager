package se.lovef.password

import java.math.BigInteger
import java.util.concurrent.ThreadLocalRandom

val random get() = ThreadLocalRandom.current()

fun randomBigInt() = BigInteger(1, randomBytes())
fun randomBigNumber() = DataNumber(randomBytes())

fun randomBytes() = randomBytes(10, 100)
fun randomBytes(length: Int) = randomBytes(length, length + 1)
fun randomBytes(from: Int, to: Int) = ByteArray(random.nextInt(from, to))
    .also { random.nextBytes(it) }

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
