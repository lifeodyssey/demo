package com.example.bff.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableGlobalAuthentication
class BasicAuthConfiguration {
    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf { it.disable() }
            .httpBasic(Customizer.withDefaults())
            .authorizeHttpRequests {
                it.requestMatchers(HttpMethod.DELETE, "/**").hasRole(
                    "ADMIN"
                )
                it.anyRequest().authenticated()
            }
        return http.build()
    }

    @Bean
    fun userDetailsService(): UserDetailsService {
        val gxp = User.withUsername("gxp")
            .password(passwordEncoder().encode("gxp"))
            .roles("USER")
            .build()
        val admin = User.withUsername("admin")
            .password(passwordEncoder().encode("admin"))
            .roles("ADMIN")
            .build()
        return InMemoryUserDetailsManager(gxp, admin)
    }
}
