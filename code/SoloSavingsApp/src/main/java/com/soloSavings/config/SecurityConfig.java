package com.soloSavings.config;

import com.soloSavings.utils.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.config.Customizer;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;


    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(new AntPathRequestMatcher("/**")).permitAll(); // Need to allow access to the landing page
                    auth.requestMatchers(new AntPathRequestMatcher("/api/login")).permitAll();
                    auth.requestMatchers(new AntPathRequestMatcher("/api/register")).permitAll();
                    auth.requestMatchers(new AntPathRequestMatcher("/api/forget-password")).permitAll();
                    auth.requestMatchers(new AntPathRequestMatcher("/api/reset-password")).permitAll();
                    auth.requestMatchers(new AntPathRequestMatcher("/solosavings/**")).permitAll(); //Any URL with pattern "/solosavings/**" do not need to be authenticated
                    auth.requestMatchers(new AntPathRequestMatcher("/api/**")).authenticated();
                    auth.anyRequest().authenticated();
                })
                .csrf((csrf) -> csrf
                    .csrfTokenRepository(new HttpSessionCsrfTokenRepository())
                    .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                    .ignoringRequestMatchers(new AntPathRequestMatcher("/**"))
                )
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