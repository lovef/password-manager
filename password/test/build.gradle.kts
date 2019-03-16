import org.gradle.api.tasks.testing.logging.TestExceptionFormat

plugins {
    kotlin("jvm")
}

group = "se.lovef"

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":password"))
    api("se.lovef:kotlin-assert-utils:0.8.2")
    api("com.nhaarman.mockitokotlin2:mockito-kotlin:2.1.0")
}

repositories {
    mavenCentral()
}

tasks.withType<Test> {
    testLogging {
        exceptionFormat = TestExceptionFormat.FULL
    }
}
