package com.wavebank.wave.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    public static final String ADMIN = "ADMIN";
    public static final String USER = "USER";
    private final JwtConverter jwtConverter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // Disable CSRF and CORS
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                // Authorization rules
                .authorizeHttpRequests(authz -> authz
                        // Public endpoints
                        .requestMatchers(HttpMethod.GET, "/api/hello").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/admin/**").hasRole(ADMIN)
                        .requestMatchers(HttpMethod.GET, "/api/user/**").hasRole(USER)
                        .requestMatchers(HttpMethod.GET, "/api/admin-and-user/**").hasAnyRole(ADMIN, USER)
                        .requestMatchers("/register/**").permitAll()
                        .requestMatchers("/users/**").hasAnyAuthority("USER", "ADMIN")
                        //OpenAPI
                        .requestMatchers(
                                "/api/v1/auth/**",
                                "/v2/api-docs",
                                "/v3/api-docs",
                                "/v3/api-docs/**",
                                "/swagger-resources",
                                "/swagger-resources/**",
                                "/configuration/ui",
                                "/configuration/security",
                                "/swagger-ui/**",
                                "webjars/**",
                                "swagger-ui.html"
                        ).permitAll()
                        // Any other requests must be authenticated
                        .anyRequest().authenticated()
                )
                // Stateless session management for JWT
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // OAuth2 Resource Server configuration
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtConverter)))
                //TODO: add OAuthLogin 
                // Optional form login for user registration
                .formLogin(Customizer.withDefaults())
                .build();
    }
}
