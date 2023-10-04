package com.soloSavings.utils;

import com.soloSavings.config.JwtUtil;
import com.soloSavings.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// TODO(will): Remove me
// https://dev.to/abhi9720/a-comprehensive-guide-to-jwt-authentication-with-spring-boot-117p
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;
    static final String JWT_TOKEN_STARTER = "Bearer ";


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        logger.info("Processing URI: " + request.getRequestURI());
        final var jwtHeader = request.getHeader("authorization");
        String username = null;
        String jwtToken = null;

        if(jwtHeader != null && jwtHeader.startsWith(JWT_TOKEN_STARTER)) {
            jwtToken = jwtHeader.substring(JWT_TOKEN_STARTER.length());
            try {
                username = jwtUtil.extractUsername(jwtToken);
            } catch (Exception e) {
                logger.error("Failed to unpacking the jwtToken with exception: " + e.getMessage());
            }
        } else {
            logger.info("Missing JWTToken");
        }

        if (username != null && null == SecurityContextHolder.getContext().getAuthentication()) {
            UserDetails userDetails = userService.loadUserByUsername(username);
            if(jwtUtil.validateToken(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                   userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
