package com.dd2pawn.pawnapi.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.dd2pawn.pawnapi.security.filter.JwtAuthenticationFilter;
import com.dd2pawn.pawnapi.security.filter.AuthRateLimitFilter;
import com.dd2pawn.pawnapi.security.jwt.AuthEntryPointJwt;
import com.dd2pawn.pawnapi.security.jwt.JwtUtils;
import com.dd2pawn.pawnapi.security.service.CustomUserDetailsService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfiguration {

        // Handles unauthorized access attempts
        private final AuthEntryPointJwt unauthorizedHandler;

        private final CustomLogoutHandler logoutHandler;

        private final AuthRateLimitFilter authRateLimitFilter;

        // JWT filter to validate tokens with every request
        @Bean
        public JwtAuthenticationFilter jwtAuthenticationFilter(JwtUtils jwtUtils,
                        CustomUserDetailsService userDetailsService) {
                return new JwtAuthenticationFilter(jwtUtils, userDetailsService);
        }

        @Bean
        // Used to authenticate user credentials and validate
        public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
                return authConfig.getAuthenticationManager();
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http,
                        JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
                http
                                .cors().and()
                                .csrf(csrf -> csrf.disable())
                                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                                .authorizeHttpRequests(auth -> auth
                                                // Authentication endpoints - public
                                                .requestMatchers("/api/auth/**").permitAll()
                                                
                                                // Pawns - public read-only (GET), authenticated write operations (POST, PUT, DELETE)
                                                .requestMatchers("GET", "/api/pawns").permitAll()
                                                .requestMatchers("GET", "/api/pawns/**").permitAll()
                                                .requestMatchers("POST", "/api/pawns").authenticated()
                                                .requestMatchers("PUT", "/api/pawns/**").authenticated()
                                                .requestMatchers("DELETE", "/api/pawns/**").authenticated()
                                                
                                                // User endpoints - authenticated
                                                .requestMatchers("/api/users/**").authenticated()
                                                .requestMatchers("/profile/**").authenticated()
                                                .requestMatchers("/me").authenticated()
                                                
                                                .anyRequest().authenticated())
                                .addFilterBefore(authRateLimitFilter, UsernamePasswordAuthenticationFilter.class)
                                .addFilterBefore(jwtAuthenticationFilter,
                                                UsernamePasswordAuthenticationFilter.class)
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
                http.logout(logout -> logout
                                .logoutUrl("/api/auth/logout")
                                .addLogoutHandler(logoutHandler)
                                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder
                                                .clearContext())
                                .permitAll());
                return http.build();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return PasswordEncoderFactories.createDelegatingPasswordEncoder();
        }
}
