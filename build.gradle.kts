plugins {
    java
    application

    id("com.gradleup.shadow") version "9.0.0-beta12"
    id("org.danilopianini.gradle-java-qa") version "1.110.0"
}

application {
    mainClass = "it.unibo.papasburgeria.Main"
}

java { toolchain { languageVersion.set(JavaLanguageVersion.of(21)) } }

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("com.github.spotbugs:spotbugs-annotations:4.9.3")
    implementation("com.google.inject:guice:7.0.0")
}
