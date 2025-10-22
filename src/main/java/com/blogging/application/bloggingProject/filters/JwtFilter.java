package com.blogging.application.bloggingProject.filters;

import com.blogging.application.bloggingProject.service.CustomUserDetailsService;
import com.blogging.application.bloggingProject.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
@Slf4j
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;
    private final HandlerExceptionResolver handlerExceptionResolver;

    @Autowired
    public JwtFilter(JwtService jwtService, CustomUserDetailsService customUserDetailsService, HandlerExceptionResolver handlerExceptionResolver) {
        this.jwtService = jwtService;
        this.customUserDetailsService = customUserDetailsService;
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            log.debug("The execution reached jwt filter");
            String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                log.debug("Jwt not found in authorization header");
                filterChain.doFilter(request, response);
                return;
            }
            String token = authHeader.substring(7);
            String username = jwtService.extractUsername(token);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
                log.debug("User Details received");
                if (jwtService.isJwtValid(userDetails, token)) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
                filterChain.doFilter(request, response);
            }
        } catch (Exception e) {
            log.error("Exception occurred while processing jwt { }", e);
            handlerExceptionResolver.resolveException(request, response, null, e);
        }
    }
}
