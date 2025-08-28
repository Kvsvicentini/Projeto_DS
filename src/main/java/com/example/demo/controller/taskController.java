package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.Task;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/tasks")
public class taskController {

    private List<Task> repository = new ArrayList<>();
    
    @GetMapping("/tasks")
    public String listTasks(Model model) {

        var tasks = List.of(
            new Task(1L, "Criar BD", "Criar bando mysql", 50, 0),
            new Task(1L, "Prototipo", "Montar o figma", 40, 0),
            new Task(1L, "Deploy", "Colocar em produção",1050, 0)
            );

        model.addAttribute("tasks", tasks);

        return "tasks";
    }

    @GetMapping("/form")
    public String showForm(){
        return "form";
    }

    @PostMapping("/form")
    public String create(@ResquestBody Task task){
        System.out.println("Cadastrando tarefa..." + task);
        return "form";
    }

}
