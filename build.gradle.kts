
plugins {
    java
    application

    id("com.gradleup.shadow") version "9.0.0-beta12"
    id("org.danilopianini.gradle-java-qa") version "1.110.0"

    id("org.danilopianini.unibo-oop-gradle-plugin") version "1.1.40"
}

java { toolchain { languageVersion.set(JavaLanguageVersion.of(21)) } }


application {
    mainClass.set("it.unibo.papasburgeria.Main")
}

java { toolchain { languageVersion.set(JavaLanguageVersion.of(21)) } }

repositories {
    mavenCentral()
}

dependencies {
    var jacksonVersion = "2.19.1"
    var tinylogVersion = "2.7.0"
    compileOnly("com.github.spotbugs:spotbugs-annotations:4.9.3")
    implementation("com.google.inject:guice:7.0.0")
    implementation("org.tinylog:tinylog-api:${tinylogVersion}")
    implementation("org.tinylog:tinylog-impl:${tinylogVersion}")
    implementation("com.fasterxml.jackson.core:jackson-databind:${jacksonVersion}")
    implementation("com.fasterxml.jackson.core:jackson-core:${jacksonVersion}")
    implementation("com.fasterxml.jackson.core:jackson-annotations:${jacksonVersion}")
    testImplementation("org.junit.jupiter:junit-jupiter:5.13.1")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}