package se.lovef.password.hasher

import se.lovef.password.Configurable

abstract class Hasher: Configurable() {
    abstract fun hash(salt: String, data: String): ByteArray
}
