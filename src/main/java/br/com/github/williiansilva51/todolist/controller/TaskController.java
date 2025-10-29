package br.com.github.williiansilva51.todolist.controller;

import br.com.github.williiansilva51.todolist.dto.task.CreateTaskDTO;
import br.com.github.williiansilva51.todolist.dto.task.TaskDTO;
import br.com.github.williiansilva51.todolist.dto.task.UpdateTaskDTO;
import br.com.github.williiansilva51.todolist.entity.Task;
import br.com.github.williiansilva51.todolist.service.TaskService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    @GetMapping
    public ResponseEntity<List<TaskDTO>> getAllTasks(@RequestParam(required = false) String name) {
        return name == null ? ResponseEntity.ok(taskService.getAllTasks()) : ResponseEntity.ok(taskService.findTasksByName(name));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@Valid @RequestBody CreateTaskDTO taskDTO) {
        Task createdTask = taskService.createTask(taskDTO);
        TaskDTO taskResponse = taskService.taskToTaskDTO(createdTask);

        return ResponseEntity.created(URI.create("/tasks/" + createdTask.getId())).body(taskResponse);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TaskDTO> completeTask(@PathVariable Long id) {
        Task completedTask = taskService.completeTask(id);
        TaskDTO taskResponse = taskService.taskToTaskDTO(completedTask);

        return ResponseEntity.ok(taskResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Long id, @RequestBody UpdateTaskDTO updateTaskDTO) {
        Task updatedTask = taskService.updateTask(id, updateTaskDTO);
        TaskDTO taskResponse = taskService.taskToTaskDTO(updatedTask);

        return ResponseEntity.ok(taskResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteTask(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.deleteTask(id));
    }
}