package org.lessons.java.spring_la_mia_pizzeria_crud.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    @SuppressWarnings("removal")
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
            .requestMatchers("/pizzas/create", "/pizzas/edit/**").hasAuthority("ADMIN")
            .requestMatchers(HttpMethod.POST, "pizzas/**").hasAuthority("ADMIN")
            .requestMatchers("/sales", "sales/**").hasAuthority("ADMIN")
            .requestMatchers("/ingredients", "ingredients/**").hasAuthority("ADMIN")
            .requestMatchers("/**").permitAll()
            .and().formLogin()
            .and().logout()
            .and().exceptionHandling();

        return http.build();
    }
}
