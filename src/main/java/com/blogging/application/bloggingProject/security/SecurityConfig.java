package com.blogging.application.bloggingProject.security;

import com.blogging.application.bloggingProject.customhandlers.CustomAccessDeniedHandler;
import com.blogging.application.bloggingProject.customhandlers.JwtAuthenticationPoint;
import com.blogging.application.bloggingProject.filters.JwtFilter;
import com.blogging.application.bloggingProject.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static com.blogging.application.bloggingProject.enums.Permission.*;
import static com.blogging.application.bloggingProject.enums.Role.ADMIN;
import static com.blogging.application.bloggingProject.enums.Role.MANAGER;
import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final JwtFilter jwtFilter;
    private final String adminRoute = "/api/v1/admin/**";
    private final String managementRoute = "/api/v1/management/**";
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final JwtAuthenticationPoint jwtAuthenticationPoint;

    @Autowired
    public SecurityConfig(JwtFilter jwtFilter, CustomAccessDeniedHandler customAccessDeniedHandler, JwtAuthenticationPoint jwtAuthenticationPoint) {
        this.jwtFilter = jwtFilter;
        this.customAccessDeniedHandler = customAccessDeniedHandler;
        this.jwtAuthenticationPoint = jwtAuthenticationPoint;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(req -> req
                        .requestMatchers("/api/v1/public/**",
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/swagger-resources/**",
                                "/webjars/**"

                        ).permitAll()
                        .requestMatchers(adminRoute).hasRole(ADMIN.name())
                        .requestMatchers(POST, adminRoute).hasAnyAuthority(ADMIN_CREATE.getPermissionName())
                        .requestMatchers(GET, adminRoute).hasAnyAuthority(ADMIN_READ.getPermissionName())
                        .requestMatchers(PUT, adminRoute).hasAnyAuthority(ADMIN_UPDATE.getPermissionName())
                        .requestMatchers(DELETE, adminRoute).hasAnyAuthority(ADMIN_DELETE.getPermissionName())
                        .requestMatchers(managementRoute).hasAnyRole(ADMIN.name(), MANAGER.name())
                        .requestMatchers(POST, managementRoute).hasAnyAuthority(ADMIN_CREATE.getPermissionName(), MANAGER_CREATE.getPermissionName())
                        .requestMatchers(GET, managementRoute).hasAnyAuthority(ADMIN_READ.getPermissionName(), MANAGER_READ.getPermissionName())
                        .requestMatchers(PUT, managementRoute).hasAnyAuthority(ADMIN_UPDATE.getPermissionName(), MANAGER_UPDATE.getPermissionName())
                        .requestMatchers(DELETE, managementRoute).hasAnyAuthority(ADMIN_DELETE.getPermissionName(), MANAGER_DELETE.getPermissionName())
                        .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(ex -> ex.accessDeniedHandler(customAccessDeniedHandler).authenticationEntryPoint(jwtAuthenticationPoint))
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }

    @Bean
    public AuthenticationProvider authenticationProvider(CustomUserDetailsService customUserDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(customUserDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        corsConfiguration.setAllowedOriginPatterns(List.of("http://localhost:8080/swagger-ui/index.html"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;

    }
}
