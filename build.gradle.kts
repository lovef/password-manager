plugins {
    id("se.lovef.git-version") version "0.3.3"
}

version = gitVersion("0.0")

println("version: $version")

subprojects.forEach {
    it.version = version
}
