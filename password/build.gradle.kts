import org.gradle.api.tasks.testing.logging.TestExceptionFormat

plugins {
    kotlin("jvm") version "1.3.21"
}

group = "se.lovef"

dependencies {
    implementation(kotlin("stdlib"))
    testImplementation(project(":password:test"))
}

repositories {
    mavenCentral()
}

tasks.withType<Test> {
    testLogging {
        exceptionFormat = TestExceptionFormat.FULL
    }
}
