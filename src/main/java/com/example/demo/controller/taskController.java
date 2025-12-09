package com.example.demo.controller;

import com.example.demo.model.Task;
import com.example.demo.service.TaskService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/tasks")
public class taskController {

    private final TaskService TaskService; // Injeção via construtor é preferida

    // Injeção de Dependência via Construtor
    public taskController(TaskService taskService) {
        this.TaskService = taskService;
    }

    // --- 1. READ (Listar Todas) ---
    // GET /tasks
    @GetMapping
    public String listTasks(Model model) {
        // Encontra todas as tarefas usando o Service
        model.addAttribute("tasks", TaskService.findAll());
        return "tasks"; // Retorna a view 'tasks.html'
    }

    // --- 2. CREATE/UPDATE (Exibir Formulário) ---
    // GET /tasks/form (para nova tarefa) ou GET /tasks/edit/{id} (para edição)
    @GetMapping({"/form", "/edit/{id}"})
    public String showForm(@PathVariable(required = false) Long id, Model model) {
        if (id != null) {
            // Se o ID existir, busca a tarefa para preencher o formulário (UPDATE)
            Task task = TaskService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID da tarefa inválido:" + id));
            model.addAttribute("task", task);
        } else if (!model.containsAttribute("task")) {
            // Se for novo e o Model não tiver a Task (erro de validação anterior), adiciona uma Task vazia
            model.addAttribute("task", new Task());
        }
        return "form"; // Retorna a view 'form.html'
    }

    // --- 3. CREATE/UPDATE (Processar o Formulário) ---
    // POST /tasks
    @PostMapping
    public String save(@Valid Task task, BindingResult result, RedirectAttributes redirect) {
        if (result.hasErrors()) {
            // Se houver erros de validação, retorna ao formulário com os erros
            return "form";
        }

        // Determina a mensagem com base se é uma criação (ID nulo) ou atualização (ID não nulo)
        String message = (task.getId() == null) ? "Tarefa cadastrada com sucesso" : "Tarefa atualizada com sucesso";
        
        TaskService.save(task); // Salva ou atualiza a tarefa via Service
        
        redirect.addFlashAttribute("message", message);
        
        return "redirect:/tasks"; // Redireciona para a lista
    }

    // --- 4. DELETE (Excluir) ---
    // GET /tasks/delete/{id}
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirect) {
        Task task = TaskService.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("ID da tarefa inválido:" + id));

        TaskService.delete(task); // Exclui a tarefa via Service
        
        redirect.addFlashAttribute("message", "Tarefa excluída com sucesso");
        
        return "redirect:/tasks";
    }
}