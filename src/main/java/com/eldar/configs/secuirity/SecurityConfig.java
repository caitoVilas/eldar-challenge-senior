package com.eldar.configs.secuirity;

import com.eldar.configs.components.JwtEntryPoint;
import com.eldar.configs.components.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * @author  caito Vilas
 * date 08/2024
 * Security configuration class
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtEntryPoint jwtEntryPoint;
    private final JwtFilter jwtFilter;

    AuthenticationManager authenticationManager;

    /**
     * Method to configure the security filter chain
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
        authenticationManager = builder.build();
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                        auth.requestMatchers(new AntPathRequestMatcher("/swagger-ui/**")).permitAll();
                        auth.requestMatchers(new AntPathRequestMatcher("/.well-known/**")).permitAll();
                        auth.requestMatchers(new AntPathRequestMatcher("/v3/api-docs/**")).permitAll();
                        auth.requestMatchers(new AntPathRequestMatcher("/users/register")).permitAll();
                        auth.requestMatchers(new AntPathRequestMatcher("/users/confirm-registration")).permitAll();
                        auth.requestMatchers(new AntPathRequestMatcher("/auth/login")).permitAll();
                        auth.requestMatchers(new AntPathRequestMatcher("/admin/**")).hasRole("ADMIN");
                        auth.anyRequest().authenticated();})
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationManager(authenticationManager)
                .exceptionHandling(ex -> ex.authenticationEntryPoint(jwtEntryPoint))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
