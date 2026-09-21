package com.medicare.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.medicare.security.CustomUserDetailsService;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(
            CustomUserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(
                        userDetailsService
                );

        provider.setPasswordEncoder(
                passwordEncoder
        );

        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration)
            throws Exception {

        return configuration
                .getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            AuthenticationProvider authenticationProvider)
            throws Exception {

        http

                /*
                 * For this REST/Postman learning phase,
                 * requests are authenticated with HTTP Basic.
                 */
                .csrf(csrf ->
                        csrf.disable()
                )

                .authenticationProvider(
                        authenticationProvider
                )

                .authorizeHttpRequests(auth ->
                        auth

                                // Health check is public
                                .requestMatchers(
                                        "/api/health"
                                )
                                .permitAll()

                                /*
                                 * Temporarily public so we can
                                 * create test users.
                                 *
                                 * Phase 9 will move registration
                                 * into /api/auth/register.
                                 */
                                .requestMatchers(
                                        HttpMethod.POST,
                                        "/api/users"
                                )
                                .permitAll()

                                // Everything else requires login
                                .anyRequest()
                                .authenticated()
                )

                .httpBasic(
                        Customizer.withDefaults()
                );

        return http.build();
    }
}