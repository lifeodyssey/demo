import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

java.sourceCompatibility = JavaVersion.VERSION_17

java.targetCompatibility = JavaVersion.VERSION_17

plugins {
    id("org.springframework.boot") version "3.1.3"
    id("io.spring.dependency-management") version "1.1.3"
    id("org.jetbrains.kotlin.jvm") version "1.9.0"
    id("org.jetbrains.kotlin.plugin.spring") version "1.9.0"
    id("org.jlleitschuh.gradle.ktlint") version "11.0.0"
    id("it.nicolasfarabegoli.conventional-commits") version "3.1.3"
    id("org.owasp.dependencycheck") version "8.4.0"
    id("org.sonarqube") version "4.3.1.3277"
//    id("org.graalvm.buildtools.native") version "0.9.24"
    jacoco
    application
    idea
}
configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}
allprojects {
    apply(plugin = "org.owasp.dependencycheck")
    apply(plugin = "application")
    apply(plugin = "jacoco")
    apply(plugin = "idea")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
//    apply(plugin = "org.graalvm.buildtools.native")
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    apply(plugin = "it.nicolasfarabegoli.conventional-commits")
    apply(plugin = "org.owasp.dependencycheck")
    apply(plugin = "org.sonarqube")

    dependencyManagement {
        imports {
            mavenBom("org.springframework.cloud:spring-cloud-dependencies:2022.0.4")
        }
    }
    group = "com.example"
    version = "0.0.2-SNAPSHOT"

    val integrationTestName = "integrationTest"
    val apiTestName = "apiTest"

    sourceSets {
        create(integrationTestName) {
            val main = sourceSets.main.get()
            val test = sourceSets.test.get()

            compileClasspath += main.output + test.output
            annotationProcessorPath += main.annotationProcessorPath + test.annotationProcessorPath
            runtimeClasspath += main.output + test.output
        }
        create(apiTestName) {
            val main = sourceSets.main.get()
            val test = sourceSets.test.get()

            compileClasspath += main.output + test.output
            annotationProcessorPath += main.annotationProcessorPath + test.annotationProcessorPath
            runtimeClasspath += main.output + test.output
        }
    }
    val integrationTestImplementation: Configuration by configurations.getting {
        extendsFrom(configurations.testImplementation.get())
    }

    val integrationTestRuntimeOnly: Configuration by configurations.getting {
        extendsFrom(configurations.testRuntimeOnly.get())
    }

    val apiTestImplementation: Configuration by configurations.getting {
        extendsFrom(configurations.testImplementation.get())
    }

    val apiTestRuntimeOnly: Configuration by configurations.getting {
        extendsFrom(configurations.testRuntimeOnly.get())
    }

    configurations {
        dependencies {
            developmentOnly("org.springframework.boot:spring-boot-devtools")

            annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
            implementation("org.jetbrains.kotlin:kotlin-reflect:1.8.10")
//    Spring Boot
            implementation("org.springframework.boot:spring-boot-starter-actuator")
            implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
            implementation("org.springframework.boot:spring-boot-starter-web")
            implementation("org.springframework.boot:spring-boot-starter:3.1.0")
            implementation("org.springframework.boot:spring-boot-starter-validation")
            implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
//
            implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.2")
            implementation("org.springframework:spring-webmvc:6.0.11")

            implementation("org.jetbrains.kotlin:kotlin-reflect")
            implementation("io.github.microutils:kotlin-logging:3.0.5")
            implementation("io.github.oshai:kotlin-logging-jvm:5.1.0")
            implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
            implementation("jakarta.validation:jakarta.validation-api")

            testImplementation("jakarta.validation:jakarta.validation-api")

            compileOnly("jakarta.servlet:jakarta.servlet-api:6.0.0")
//            implementation("org.springframework.security:spring-security-config")
//            implementation("org.springframework.security:spring-security-web")

            // kotlin
            implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
            // kotlin test
            implementation("org.testng:testng:7.7.0")
            testImplementation(kotlin("test"))
            testImplementation("de.flapdoodle.embed:de.flapdoodle.embed.mongo.spring30x:4.6.1")
            testImplementation("io.mockk:mockk:1.13.4")
            testImplementation("com.ninja-squad:springmockk:4.0.0")
            testImplementation("org.springframework.boot:spring-boot-starter-test") {
                exclude(module = "mockito-core")
            }
            // mongodb migration
            implementation("io.mongock:mongock-springboot:5.2.2")
            implementation("io.mongock:mongodb-springdata-v4-driver:5.2.1")
            implementation("io.mongock:mongock-springboot-v3:5.2.1")

            integrationTestImplementation("org.springframework.cloud:spring-cloud-contract-wiremock")
            annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

            // grpc
            implementation(kotlin("script-runtime"))
        }

        repositories {
            mavenCentral()
        }
        tasks.withType<Test> {
            useJUnitPlatform()
        }
//    conventionalCommits {
//        // This configuration is based on : https://github.com/nicolasfara/conventional-commits
//        warningIfNoGitRoot = true
//
//        successMessage = "Commit message meets Conventional Commit standards..."
//
//        failureMessage = "The commit message does not meet the Conventional Commit standard, please check again..."
//    }

        jacoco {
            toolVersion = "0.8.9"
            reportsDirectory.set(layout.buildDirectory.dir("customJacocoReportDir"))
            applyTo(tasks.run.get())
        }

        tasks.build {
            dependsOn(tasks.jacocoTestCoverageVerification)
        }

        tasks.check {
            dependsOn(tasks.jacocoTestCoverageVerification, "gitLocalHooks")
        }
        val integrationTest = task<Test>(integrationTestName) {
            group = "verification"
            description = "Runs integration tests."
            testClassesDirs = sourceSets[integrationTestName].output.classesDirs
            classpath = sourceSets[integrationTestName].runtimeClasspath
            maxHeapSize = "2g"

            useJUnitPlatform()
            mustRunAfter(tasks["test"])
        }

        val apiTest = task<Test>(apiTestName) {
            group = "verification"
            description = "Runs api tests."
            testClassesDirs = sourceSets[apiTestName].output.classesDirs
            classpath = sourceSets[apiTestName].runtimeClasspath
            maxHeapSize = "2g"
            val profile = project.properties["spring.profiles.active"]
            systemProperties("spring.profiles.active" to profile)

            useJUnitPlatform()
        }
        tasks.test {
            finalizedBy(tasks.jacocoTestReport) // report is always generated after tests run
        }
        tasks.register<JacocoReport>("applicationCodeCoverageReport") {
            executionData(tasks.run.get())
            sourceSets(sourceSets.main.get())
        }
        val testCoverageExclusions = listOf(
            "**/*Application*",
            "**/exception/**",
            "**/config/**",
            "**/repository/**",
            "**/migration/**",
            "**/dto/**"
        )

        val excludeTestFiles: FileTree = sourceSets.main.get().output.asFileTree.matching {
            testCoverageExclusions.map { exclude(it) }.toTypedArray()
        }
        tasks.jacocoTestReport {
            executionData(tasks["test"], tasks[integrationTestName])
            dependsOn(tasks.test, integrationTest)
            classDirectories.setFrom(excludeTestFiles)
            reports {
                xml.required.set(false)
                csv.required.set(false)
                html.outputLocation.set(layout.buildDirectory.dir("jacocoHtml"))
            }
        }

        tasks.jacocoTestCoverageVerification {
            executionData(tasks["test"], tasks[integrationTestName])
            classDirectories.setFrom(excludeTestFiles)
            violationRules {
                rule {
                    limit {
                        minimum = "0.8".toBigDecimal()
                    }
                }
//                rule {
//                    limit {
//                        counter = "BRANCH"
//                        value = "COVEREDRATIO"
//                        minimum = "0.9".toBigDecimal()
//                    }
//                }
            }
        }

        tasks.create("gitLocalHooks") {
            doLast {
                println("Adding local hook directory!")
                Runtime.getRuntime().exec("git config --local core.hooksPath .githooks")
            }
        }

        tasks.build {
            dependsOn(tasks.jacocoTestCoverageVerification)
        }
        tasks.withType<KotlinCompile> {
            kotlinOptions {
                freeCompilerArgs = listOf("-Xjsr305=strict")
                jvmTarget = "17"
            }
        }
    }
}
