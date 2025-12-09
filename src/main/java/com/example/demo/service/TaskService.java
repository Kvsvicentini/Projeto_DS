package com.example.demo.service;

import com.example.demo.model.Task;
import com.example.demo.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service // 1. Indica que esta classe é a camada de serviço/lógica de negócios
public class TaskService {

    @Autowired // 2. Injeta o Repositório para acesso ao banco de dados
    private TaskRepository taskRepository;

    // --- CREATE/UPDATE ---
    public Task save(Task task) {
        // Exemplo de Lógica de Negócios:
        // if (task.getTitle().isEmpty()) { throw new IllegalArgumentException("Título
        // vazio!"); }

        return taskRepository.save(task);
    }

    // --- READ (Buscar Todos) ---
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    // --- READ (Buscar por ID) ---
    public Optional<Task> findById(Long id) {
        return taskRepository.findById(id);
    }

    // --- DELETE ---
    public void deleteById(Long id) {
        taskRepository.deleteById(id);
    }

    // Método DELETE sobrecarregado (recebe a entidade)
    public void delete(Task task) {
        taskRepository.delete(task);
    }
}