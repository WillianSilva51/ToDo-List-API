package br.com.github.williiansilva51.todolist.controller;

import br.com.github.williiansilva51.todolist.dto.task.CreateTaskDTO;
import br.com.github.williiansilva51.todolist.dto.task.TaskDTO;
import br.com.github.williiansilva51.todolist.dto.task.UpdateTaskDTO;
import br.com.github.williiansilva51.todolist.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @PostMapping
    public ResponseEntity<CreateTaskDTO> createTask(@RequestBody CreateTaskDTO taskDTO) {
        if (taskService.createTask(taskDTO)) {
            return ResponseEntity.ok(taskDTO);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Boolean> updateTask(@PathVariable Long id, @RequestBody UpdateTaskDTO updateTaskDTO) {
        return ResponseEntity.ok(taskService.updateTask(id, updateTaskDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteTask(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.deleteTask(id));
    }
}