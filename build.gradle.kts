plugins {
	java
	id("org.springframework.boot") version "2.7.11"
	id("io.spring.dependency-management") version "1.0.15.RELEASE"
}

group = "com.example.imagecompress"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
	mavenCentral()
}

dependencies {
	implementation("commons-io:commons-io:2.11.0")
	implementation("com.github.ben-manes.caffeine:caffeine:2.9.3")
	implementation("org.springframework.boot:spring-boot-starter-cache")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.awaitility:awaitility:4.2.0")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
