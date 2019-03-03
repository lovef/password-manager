plugins {
    kotlin("jvm") version "1.3.21"
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.java-websocket:Java-WebSocket:1.4.0")
}

repositories {
    mavenCentral()
}
