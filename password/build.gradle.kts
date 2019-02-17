plugins {
    kotlin("jvm") version "1.3.21"
}

group = "se.lovef"

dependencies {
    implementation(kotlin("stdlib"))
    testImplementation("se.lovef:kotlin-assert-utils:0.8.0")
}

repositories {
    mavenCentral()
}
