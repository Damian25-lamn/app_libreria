plugins {
    java
    application
    //id("org.javamodularity.moduleplugin") version "1.8.12"
    id("org.openjfx.javafxplugin") version "0.1.0"
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

    //modularity.inferModulePath = true
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }

}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

application {
    mainModule.set("org.programacion.avanzada.bookstoreapp")
    mainClass.set("org.programacion.avanzada.bookstoreapp.HelloApplication")
    applicationDefaultJvmArgs = listOf("-Xmx512m", "-Xms256m")
}

javafx {
    version = "21.0.1"
    modules = listOf("javafx.controls", "javafx.fxml")
}

dependencies {
    // --- Controles y estilos adicionales para JavaFX ---
    implementation("org.controlsfx:controlsfx:11.2.1") // Librería con componentes avanzados para JavaFX
    implementation("org.kordamp.bootstrapfx:bootstrapfx-core:0.4.0") // Estilos CSS estilo Bootstrap para JavaFX

    // --- Dependencias para pruebas con JUnit Jupiter ---
    testImplementation("org.junit.jupiter:junit-jupiter-api:${junitVersion}") // API principal de JUnit 5
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${junitVersion}") // Motor de ejecución para JUnit 5

    // --- Spring Core y sus módulos esenciales ---
    implementation("org.springframework:spring-core:6.2.8") // Núcleo del framework: beans, lifecycle, IoC
    implementation("org.springframework:spring-context:6.2.8") // Contexto de aplicación: @ComponentScan, @Service, etc.
    implementation("org.springframework:spring-jdbc:6.2.8") // JdbcTemplate, NamedParameterJdbcTemplate, soporte JDBC
    implementation("org.springframework:spring-tx:6.2.8") // Transacciones programáticas y declarativas con @Transactional

    // --- Spring Data JDBC ---
    implementation("org.springframework.data:spring-data-jdbc:3.5.1") // Repositorios tipo CRUD con JDBC
    implementation("org.springframework.data:spring-data-relational:3.5.1") // JdbcConverter, acceso relacional, mapeo de entidades
    implementation("org.springframework.data:spring-data-commons:3.5.1") // Interfaces comunes a todos los módulos Spring Data

    // --- Base de datos: conexión y driver ---
    implementation("com.zaxxer:HikariCP:5.0.1") // Pool de conexiones recomendado por Spring
    implementation("org.postgresql:postgresql:42.7.7") // Driver JDBC oficial para PostgreSQL

    // --- JavaFX (UI nativa para escritorio) ---
    implementation("org.openjfx:javafx-controls:21.0.1") // Controles estándar: botones, listas, etc.
    implementation("org.openjfx:javafx-fxml:21.0.1") // Soporte para archivos FXML (diseño de interfaces en XML)

    // --- Logging simple con SLF4J ---
    implementation("org.slf4j:slf4j-simple:2.0.13") // Implementación simple de SLF4J (para salida en consola)}

    //Actualizacion de Lombok
    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")
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

