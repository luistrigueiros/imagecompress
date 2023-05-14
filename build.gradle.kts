plugins {
	java
	id("org.springframework.boot") version "2.7.11"
	id("io.spring.dependency-management") version "1.0.15.RELEASE"
	id("me.champeau.jmh") version "0.7.1"
}

group = "com.example.imagecompress"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
	mavenCentral()
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

dependencies {
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
	implementation("commons-io:commons-io:2.11.0")
	implementation("com.github.ben-manes.caffeine:caffeine:2.9.3")
	implementation("org.springframework.boot:spring-boot-starter-web")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.awaitility:awaitility:4.2.0")
	testImplementation("org.junit.jupiter:junit-jupiter-params")
}

tasks.withType<Test> {
	useJUnitPlatform()
	failFast = false
}

jmh {
	benchmarkMode.set(listOf("all"))
	warmupIterations.set(2)
	iterations.set(2)
	fork.set(2)
}
