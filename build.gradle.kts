plugins {
    kotlin("jvm") version "1.9.22"
}

group = "com.platform"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.postgresql:postgresql:42.7.2")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
