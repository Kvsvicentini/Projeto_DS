package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    /**
     * Mapeia a URL raiz ("/") para a view index.html.
     */
    @GetMapping("/")
    public String index() {
        return "index"; // Retorna o nome do arquivo da view (index.html)
    }
}