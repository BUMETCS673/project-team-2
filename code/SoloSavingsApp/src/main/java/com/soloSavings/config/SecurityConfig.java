package com.soloSavings.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
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
                .addFilter(new JwtFilter(authenticationManager()))
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .httpBasic(Customizer.withDefaults())
                .formLogin(withDefaults());     //better to change to our own login form

        return http.build();
    }
    @Bean
    public AuthenticationManager authenticationManager() {
        return authenticationManager();
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