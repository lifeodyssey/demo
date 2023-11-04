tasks.getByName<Jar>("jar") {
    enabled = false
}

configurations {
    dependencies {
        implementation("org.springframework.security:spring-security-web")
        implementation("org.springframework.security:spring-security-config")
    }
}
