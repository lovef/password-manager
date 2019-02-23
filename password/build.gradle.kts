import org.gradle.api.tasks.testing.logging.TestExceptionFormat

plugins {
    kotlin("jvm") version "1.3.21"
}

group = "se.lovef"

dependencies {
    implementation(kotlin("stdlib"))
    testImplementation("se.lovef:kotlin-assert-utils:0.8.2")
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.1.0")
}

repositories {
    mavenCentral()
}

tasks.withType<Test> {
    testLogging {
        exceptionFormat = TestExceptionFormat.FULL
    }
}
