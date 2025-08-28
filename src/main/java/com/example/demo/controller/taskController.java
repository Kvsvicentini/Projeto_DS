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

    @GetMapping
    public String listTasks(Model model){
        model.addAttribute("tasks", repository);
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
