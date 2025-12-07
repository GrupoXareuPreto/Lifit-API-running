// src/main/java/br/com/xareu/lift/Config/SecurityConfig.java
package br.com.xareu.lift.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/usuario").permitAll()
                        .requestMatchers(HttpMethod.GET, "/usuario", "/usuario/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/metas/publicas").permitAll()
                        .requestMatchers(HttpMethod.GET, "/evento/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/seguidor/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/seguidor/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/seguidor/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/curtida").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/curtida/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/comentario").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/comentario/**").authenticated()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}