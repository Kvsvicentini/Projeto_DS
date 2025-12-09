package com.example.demo.config; // Pacote ajustado para o escaneamento correto

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // Habilita a integração de segurança web
public class SecurityConfig {

        // --- 1. Bean: PasswordEncoder (CRUCIAL) ---
        // Define o codificador de senha (BCrypt é o padrão forte)
        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        // --- 2. Bean: SecurityFilterChain (Configuração de Autorização) ---
        // Define as regras de acesso e o fluxo de login/logout
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                // Configurações de Estado e Headers (Importante para H2 Console)
                                .csrf(csrf -> csrf
                                                // Ignora CSRF para URLs públicas que recebem POST (Registro, H2)
                                                .ignoringRequestMatchers("/h2-console/**", "/auth/register"))
                                .headers(headers -> headers
                                                // Permite que o console H2 seja exibido dentro de um iframe
                                                .frameOptions(frameOptions -> frameOptions.sameOrigin()))

                                // Regras de Autorização de Requisições
                                .authorizeHttpRequests(auth -> auth
                                                // Permite acesso público à raiz, login, registro e console H2
                                                .requestMatchers("/", "/login", "/auth/register", "/h2-console/**")
                                                .permitAll()

                                                // Exige autenticação para todas as rotas de tarefas (o núcleo do app)
                                                .requestMatchers("/tasks/**").authenticated()

                                                // Protege todas as outras requisições por padrão
                                                .anyRequest().authenticated())

                                // Configuração de Login por Formulário (MVC padrão)
                                .formLogin(form -> form
                                                .loginPage("/login") // Se você tiver uma página de login customizada
                                                .defaultSuccessUrl("/tasks", true) // Redireciona para /tasks após o
                                                                                   // login
                                                .permitAll() // Permite acesso a esta configuração para todos
                                )

                                // Configuração de Logout
                                .logout(logout -> logout
                                                .logoutUrl("/logout") // URL para processar o logout
                                                .logoutSuccessUrl("/login?logout") // Redireciona para login após o
                                                                                   // logout
                                                .permitAll());

                return http.build();
        }
}