plugins {
    java
    application

    id("com.gradleup.shadow") version "9.0.0-beta12"
    id("org.danilopianini.gradle-java-qa") version "1.110.0"
}

application {
    mainClass.set("it.unibo.papasburgeria.Main")
}

java { toolchain { languageVersion.set(JavaLanguageVersion.of(21)) } }

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("com.github.spotbugs:spotbugs-annotations:4.9.3")
    implementation("com.google.inject:guice:7.0.0")
    implementation("org.tinylog:tinylog-api:2.7.0")
    implementation("org.tinylog:tinylog-impl:2.7.0")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.19.1")
    implementation("com.fasterxml.jackson.core:jackson-core:2.19.1")
    implementation("com.fasterxml.jackson.core:jackson-annotations:2.19.1")
    testImplementation("org.junit.jupiter:junit-jupiter:15.13.1")
}