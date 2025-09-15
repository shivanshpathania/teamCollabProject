// File: src/main/java/com/example/config/SecurityConfig.java
package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Store and compare passwords in plain text (NOT SECURE)
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
                // Allow access to static resources, login, and signup pages
                .antMatchers("/css/**", "/login", "/signup").permitAll()
                // Secure student pages to the STUDENT role
                .antMatchers("/student/**").hasRole("STUDENT")
                // Secure mentor pages to the MENTOR role
                .antMatchers("/mentor/**", "/announcement/**").hasRole("MENTOR")
                // All other requests must be authenticated
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                // Specify the custom login page URL
                .loginPage("/login")
                // URL to submit the username and password to
                .loginProcessingUrl("/login")
                // Redirect to a custom success handler after login
                .successHandler(new RoleBasedAuthenticationSuccessHandler())
                .permitAll()
            )
            .logout(logout -> logout
                // URL to trigger logout
                .logoutUrl("/logout")
                // URL to redirect to after logout
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            );

        return http.build();
    }
}