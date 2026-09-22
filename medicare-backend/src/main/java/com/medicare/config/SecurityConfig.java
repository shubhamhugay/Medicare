package com.medicare.config;

import com.medicare.security.CustomAccessDeniedHandler;
import com.medicare.security.CustomAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.medicare.security.CustomUserDetailsService;
import com.medicare.security.JwtAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    CustomAccessDeniedHandler customAccessDeniedHandler;
    CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    public SecurityConfig(CustomAccessDeniedHandler customAccessDeniedHandler, CustomAuthenticationEntryPoint customAuthenticationEntryPoint) {
        this.customAccessDeniedHandler = customAccessDeniedHandler;
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
    }

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
            AuthenticationProvider authenticationProvider,
            JwtAuthenticationFilter jwtAuthenticationFilter)
            throws Exception {

        http

                .csrf(csrf ->
                        csrf.disable()
                )

                /*
                 * JWT authentication is stateless.
                 * Spring Security should not create
                 * an authentication session.
                 */
                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                .authenticationProvider(
                        authenticationProvider
                )

                .authorizeHttpRequests(auth ->
                                auth

                                        // Public APIs
                                        .requestMatchers(
                                                "/api/health",
                                                "/api/auth/register",
                                                "/api/auth/login"
                                        )
                                        .permitAll()

                                        // Current user's profile
                                        .requestMatchers(
                                                "/api/users/me"
                                        )
                                        .authenticated()

                                        // Doctor can manage only own profile
                                        .requestMatchers(
                                                "/api/doctors/profile",
                                                "/api/doctors/profile/**"
                                        )
                                        .hasRole("DOCTOR")

                                        // Doctor search/listing
                                        .requestMatchers(
                                                HttpMethod.GET,
                                                "/api/doctors/**"
                                        )
                                        .hasAnyRole(
                                                "PATIENT",
                                                "DOCTOR"
                                        )

                                        // Patient books appointments
                                        .requestMatchers(
                                                HttpMethod.POST,
                                                "/api/appointments"
                                        )
                                        .hasRole("PATIENT")

                                        // Patient appointment history
                                        .requestMatchers(
                                                "/api/appointments/my"
                                        )
                                        .hasRole("PATIENT")

                                        // Doctor schedule
                                        .requestMatchers(
                                                "/api/appointments/doctor/**"
                                        )
                                        .hasRole("DOCTOR")

                                        // Patient cancellation
                                        .requestMatchers(
                                                HttpMethod.PATCH,
                                                "/api/appointments/*/cancel"
                                        )
                                        .hasRole("PATIENT")

                                        // Doctor completion
                                        .requestMatchers(
                                                HttpMethod.PATCH,
                                                "/api/appointments/*/complete"
                                        )
                                        .hasRole("DOCTOR")

                                        // Appointment details
                                        .requestMatchers(
                                                HttpMethod.GET,
                                                "/api/appointments/*"
                                        )
                                        .hasAnyRole(
                                                "PATIENT",
                                                "DOCTOR"
                                        )
// Doctor creates prescription
                                        .requestMatchers(
                                                HttpMethod.POST,
                                                "/api/prescriptions"
                                        )
                                        .hasRole("DOCTOR")


// Patient views all own prescriptions
                                        .requestMatchers(
                                                HttpMethod.GET,
                                                "/api/prescriptions/my"
                                        )
                                        .hasRole("PATIENT")


// Patient or assigned doctor can view
// prescription for an appointment
                                        .requestMatchers(
                                                HttpMethod.GET,
                                                "/api/prescriptions/appointment/*"
                                        )
                                        .hasAnyRole(
                                                "PATIENT",
                                                "DOCTOR"
                                        )
                                        /*
                                         * Safer default:
                                         * anything we forgot to configure
                                         * is denied.
                                         */
                                        .anyRequest()
                                        .denyAll()
                )
                /*
                 * Return 401 when authentication
                 * is missing or invalid.
                 */
                .exceptionHandling(exception ->
                        exception

                                .authenticationEntryPoint(
                                        customAuthenticationEntryPoint
                                )

                                .accessDeniedHandler(
                                        customAccessDeniedHandler
                                )
                )

                /*
                 * JWT must be checked before
                 * username/password authentication filter.
                 */
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}