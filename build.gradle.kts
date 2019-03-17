plugins {
    id("se.lovef.git-version") version "0.3.3"
}

group = "se.lovef"
version = gitVersion("0.0")

println("version: $version")

subprojects.forEach {
    it.version = version
    it.group = group
}
