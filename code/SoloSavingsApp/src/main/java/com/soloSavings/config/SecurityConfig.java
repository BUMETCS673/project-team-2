package com.soloSavings.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
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
                    auth.requestMatchers(new AntPathRequestMatcher("/solosavings/**")).permitAll(); //Any URL with pattern "/solosavings/**" do not need to be authenticated
                    auth.anyRequest().authenticated();
                })
                .securityMatcher("/api/**") //Any request with pattern "/api/**" needs to be authenticated
                .securityMatcher("/dashboard/**")   //Any URL with pattern "/dashboard/**" needs to be authenticated
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .httpBasic(Customizer.withDefaults())
                .formLogin(withDefaults());
        return http.build();
    }

}