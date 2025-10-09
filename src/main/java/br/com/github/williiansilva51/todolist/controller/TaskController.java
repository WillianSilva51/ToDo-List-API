package br.com.github.williiansilva51.todolist.controller;

import br.com.github.williiansilva51.todolist.dto.TaskDTO;
import br.com.github.williiansilva51.todolist.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/task")
class TaskController {
    private TaskService taskService;

    @GetMapping
    public ResponseEntity<List<TaskDTO>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @GetMapping(params = "id")
    public ResponseEntity<TaskDTO> getTaskById(Long id) {
        return ResponseEntity.ok(taskService.getTaskById(id).orElseThrow());
    }
}