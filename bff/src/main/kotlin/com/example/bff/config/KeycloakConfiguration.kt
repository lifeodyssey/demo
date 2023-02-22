package com.example.bff.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class KeycloakConfiguration {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.authorizeHttpRequests().anyRequest().authenticated()
            // any request should be authenticated
            .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            // the session should be stateless
            .and().cors().disable()
            // disable cors
            .oauth2ResourceServer().jwt()
        return http.build()
    }
}
