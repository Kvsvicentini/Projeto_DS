package config;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;

import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfig {

    public SecurityFilterChain config(HttpSecurity http) throws Exception{
        return http
                    .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/tasks/**").authenticated()
                    )
                    .oauth2Login(Customizer.withDefaults())
                    .build();

    }
    
    
}
