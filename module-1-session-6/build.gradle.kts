plugins {
    id("java")
}

group = "org.dti.se"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.openjdk.jmh:jmh-core:latest.release")
    implementation("org.openjdk.jmh:jmh-generator-annprocess:latest.release")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    annotationProcessor("org.openjdk.jmh:jmh-generator-annprocess:latest.release")
}

tasks.test {
    useJUnitPlatform()
}