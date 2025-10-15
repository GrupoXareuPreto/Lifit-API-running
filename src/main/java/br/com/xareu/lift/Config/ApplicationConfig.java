// src/main/java/br/com/xareu/lift/Config/ApplicationConfig.java
package br.com.xareu.lift.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ApplicationConfig {

    // Este Bean agora vive de forma independente e pode ser injetado
    // em qualquer lugar sem causar ciclos de dependência com a segurança web.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}