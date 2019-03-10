package se.lovef.password

abstract class Configurable {
    abstract val configuration: String

    override fun toString() = configuration

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Configurable

        if (configuration != other.configuration) return false

        return true
    }

    override fun hashCode(): Int {
        return configuration.hashCode()
    }
}
