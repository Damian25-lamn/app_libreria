plugins {
    java
    application
    id("org.javamodularity.moduleplugin") version "1.8.12"
    id("org.openjfx.javafxplugin") version "0.0.13"
    id("org.beryx.jlink") version "2.25.0"
    id("io.freefair.lombok") version "8.13.1"
}

group = "org.programacion.avanzada"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val junitVersion = "5.10.2"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

application {
    mainModule.set("org.programacion.avanzada.bookstoreapp")
    mainClass.set("org.programacion.avanzada.bookstoreapp.HelloApplication")
}

javafx {
    version = "21"
    modules = listOf("javafx.controls", "javafx.fxml")
}

dependencies {
    implementation("org.controlsfx:controlsfx:11.2.1")
    implementation("org.kordamp.bootstrapfx:bootstrapfx-core:0.4.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:${junitVersion}")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${junitVersion}")

    // Spring Core
    // Núcleo del framework Spring (inyección de dependencias, ciclo de vida de beans, etc.)
    implementation("org.springframework:spring-core:6.2.8")
    // Spring Data JDBC: repositorios tipo CRUD y mapeo de entidades usando JDBC sin JPA
    implementation("org.springframework.data:spring-data-jdbc:3.5.1")
    // Incluye clases necesarias como JdbcConverter, RelationalDataAccessStrategy, etc.
    implementation("org.springframework.data:spring-data-relational:3.2.5")
    // Proporciona soporte de JDBC tradicional: NamedParameterJdbcTemplate, etc.
    implementation("org.springframework:spring-jdbc:6.2.8")
    // HikariCP: pool de conexiones recomendado por Spring Boot
    implementation("com.zaxxer:HikariCP:5.0.1")
    // Driver oficial de PostgreSQL
    implementation("org.postgresql:postgresql:42.7.7")

    // JavaFX (ajustar a tu sistema)
    implementation("org.openjfx:javafx-controls:21.0.1")
    implementation("org.openjfx:javafx-fxml:21.0.1")

    implementation("org.slf4j:slf4j-simple:2.0.13")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

jlink {
    imageZip.set(layout.buildDirectory.file("/distributions/app-${javafx.platform.classifier}.zip"))
    options.set(listOf("--strip-debug", "--compress", "2", "--no-header-files", "--no-man-pages"))
    launcher {
        name = "app"
    }
}
