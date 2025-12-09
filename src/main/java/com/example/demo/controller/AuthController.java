package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;


@Controller
@RequestMapping("/auth")
public class AuthController {

    // Declaração dos campos como final (recomendado)
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Construtor: O Spring injeta as dependências automaticamente aqui
    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // --- 1. GET: Mostrar Formulário de Login ---
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    // --- 2. GET: Mostrar Formulário de Registro ---
    @GetMapping("/register")
    public String showRegisterForm(User user) {
        return "register_form";
    }

    // --- 3. POST: Processar Registro ---
    @PostMapping("/register")
    public String registerUser(@Valid User user, BindingResult result, RedirectAttributes redirect) {

        // 1. Verifica erros de validação
        if (result.hasErrors()) {
            return "register_form";
        }

        // 2. Verifica se o username já existe
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            // Adiciona erro específico para o campo 'username'
            result.rejectValue("username", "user.username", "Este nome de usuário já está em uso.");
            return "register_form";
        }

        // 3. Codifica e define a senha
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        // **Configura a role padrão (se a entidade User não tiver um valor padrão)**
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("USER");
        }

        // 4. Salva o novo usuário
        userRepository.save(user);

        redirect.addFlashAttribute("message", "Registro realizado com sucesso! Faça login.");

        // Redireciona para a página de login para que o usuário inicie a sessão
        return "/auth/login";
    }

    
}