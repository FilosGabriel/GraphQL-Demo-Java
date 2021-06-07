plugins {
    java
    id("org.springframework.boot") version "2.5.0"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("com.netflix.dgs.codegen") version "4.6.4"

}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {

//  SPRING BOOT
    implementation("org.springframework.boot:spring-boot-starter-web")
    developmentOnly("org.springframework.boot:spring-boot-devtools")


//    GraphQL
    implementation("com.netflix.graphql.dgs:graphql-dgs-spring-boot-starter:latest.release")

//   TESTING
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")

}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

tasks.getByName<com.netflix.graphql.dgs.codegen.gradle.GenerateJavaTask>("generateJava") {
    packageName = "movie_graph.model"
    schemaPaths = listOf("${projectDir}/src/main/resources/schema").toMutableList()
    generateClient = true
}
//generateJava{
//    schemaPaths = ["${projectDir}/src/main/resources/schema"] // List of directories containing schema files
//    packageName = 'com.example.packagename' // The package name to use to generate sources
//    generateClient = true // Enable generating the type safe query API
//}