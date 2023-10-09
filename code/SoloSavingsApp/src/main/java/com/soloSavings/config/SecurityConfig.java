package com.soloSavings.config;

import com.soloSavings.utils.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                //.csrf(Customizer.withDefaults()) // TODO(Will): I don't have time to fix CSRF right now...
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(new AntPathRequestMatcher("/**")).permitAll(); // Need to allow access to the landing page
                    auth.requestMatchers(new AntPathRequestMatcher("/api/login")).permitAll();
                    auth.requestMatchers(new AntPathRequestMatcher("/api/register")).permitAll();
                    auth.requestMatchers(new AntPathRequestMatcher("/api/forget-password")).permitAll();
                    auth.requestMatchers(new AntPathRequestMatcher("/api/reset-password")).permitAll();
                    auth.requestMatchers(new AntPathRequestMatcher("/solosavings/**")).permitAll(); //Any URL with pattern "/solosavings/**" do not need to be authenticated
                    auth.requestMatchers(new AntPathRequestMatcher("/api/**")).authenticated();
                })
                .addFilterAfter(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .httpBasic(withDefaults())
                .formLogin(withDefaults());     //better to change to our own login form
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

    @Bean
    public AuthenticationManager authenticationManager() throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }
}