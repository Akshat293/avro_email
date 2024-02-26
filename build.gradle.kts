plugins {
    id("java")
}

group = "com.ms.avro"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    // Avro
    implementation("org.apache.avro:avro:1.11.1")
    implementation("org.apache.avro:avro-compiler:1.11.1")

    // lombok
    implementation("org.projectlombok:lombok:1.16.18")
}

tasks.test {
    useJUnitPlatform()
}