package com.soloSavings.config;

//import com.auth0.jwt.JWT;
//import com.auth0.jwt.algorithms.Algorithm;
import com.soloSavings.model.User;
//import com.soloSavings.serviceImpl.TokenManagerServiceImpl;
//import com.soloSavings.utils.TokenFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private static final int EXPIRATION_TIME = 5 * 60 * 1000;
    private static final String SECRET = "TODO(will): Make this better";
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(new AntPathRequestMatcher("/api/login")).permitAll();
                    auth.requestMatchers(new AntPathRequestMatcher("/api/register")).permitAll();
                    auth.requestMatchers(new AntPathRequestMatcher("/api/**")).authenticated();
                    auth.requestMatchers(new AntPathRequestMatcher("/solosavings/**")).permitAll(); //Any URL with pattern "/solosavings/**" do not need to be authenticated
                    auth.anyRequest().authenticated();
                })
                //.securityMatcher("/api/**") //Any request with pattern "/api/**" needs to be authenticated
                .securityMatcher("/dashboard/**")   //Any URL with pattern "/dashboard/**" needs to be authenticated
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .httpBasic(Customizer.withDefaults())
                .formLogin(withDefaults());
        //http.addFilter(new TokenFilter());
        return http.build();
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public static String hashedPassword(String plaintTextPassword) {
        PasswordEncoder encoder = passwordEncoder();
        return encoder.encode(plaintTextPassword);
    }

    public static boolean checkPassword(String cipherPassword, String plaintTextPassword) {
        PasswordEncoder encoder = passwordEncoder();
        return encoder.matches(plaintTextPassword, cipherPassword);
    }
}