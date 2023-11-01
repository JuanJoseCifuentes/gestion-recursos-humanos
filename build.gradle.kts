plugins {
    java
    id("org.springframework.boot") version "2.7.15"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    id("info.solidsoft.pitest") version "1.9.0"
    jacoco
    id("org.sonarqube") version "4.4.1.3373"
}

val clasesExcluidas = setOf(
        "**.dto.**",
        "**.entidad.**"
)
group = "co.edu.unisabana"

version = "0.0.1-SNAPSHOT"



pitest {
    junit5PluginVersion = "1.0.0"
    excludedClasses.set(clasesExcluidas)
}

java {
    sourceCompatibility = JavaVersion.VERSION_19
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("mysql:mysql-connector-java:8.0.30")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("com.h2database:h2:2.2.220")

    compileOnly("org.projectlombok:lombok")

    annotationProcessor("org.projectlombok:lombok")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
tasks.test {
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        csv.required.set(true)
        xml.required.set(true)
        html.required.set(true)
    }
}

jacoco {
    toolVersion = "0.8.8"
}

tasks.withType<JacocoReport> {
    afterEvaluate {
        classDirectories.setFrom(files(classDirectories.files.map {
            fileTree(it).apply {
                exclude( "**/dto**")
                exclude( "**/entidad**")
            }
        }))
    }
}

sonarqube {
    properties {
        property("sonar.projectName", "RH-Manager-APP")
        property("sonar.java.coveragePlugin", "jacoco")
        property("sonar.coverage.jacoco.xmlReportPath", "${project.buildDir}/reports/jacoco.xml")
    }
}