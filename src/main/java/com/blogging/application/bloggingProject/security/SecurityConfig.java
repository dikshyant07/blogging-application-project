package com.blogging.application.bloggingProject.security;

import com.blogging.application.bloggingProject.customhandlers.CustomAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
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
    private final String adminRoute = "/api/v1/admin/**";
    private final String managementRoute = "/api/v1/management/**";
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    @Autowired
    public SecurityConfig(CustomAccessDeniedHandler customAccessDeniedHandler) {
        this.customAccessDeniedHandler = customAccessDeniedHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(req -> req
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
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(ex -> ex.accessDeniedHandler(customAccessDeniedHandler))
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        corsConfiguration.setAllowedOriginPatterns(List.of("http://localhost:3000"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;

    }
}
