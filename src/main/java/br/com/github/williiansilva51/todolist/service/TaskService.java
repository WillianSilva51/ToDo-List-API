package br.com.github.williiansilva51.todolist.service;

import br.com.github.williiansilva51.todolist.dto.task.CreateTaskDTO;
import br.com.github.williiansilva51.todolist.dto.task.TaskDTO;
import br.com.github.williiansilva51.todolist.dto.task.UpdateTaskDTO;
import br.com.github.williiansilva51.todolist.entity.Task;
import br.com.github.williiansilva51.todolist.handler.ResourceNotFoundException;
import br.com.github.williiansilva51.todolist.repository.TaskRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskDTO getTaskById(Long id) {
        return taskRepository.findById(id)
                .map(task -> new TaskDTO(task.getTitle(), task.getDescription(), task.getCompleted(), task.getCreatedAt(), task.getCompletedAt()))
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
    }

    public List<TaskDTO> getAllTasks() {
        return taskRepository.findAll()
                .stream()
                .map(task -> new TaskDTO(task.getTitle(), task.getDescription(), task.getCompleted(), task.getCreatedAt(), task.getCompletedAt()))
                .toList();
    }

    public boolean createTask(CreateTaskDTO taskDTO) {
        taskRepository.save(new Task(taskDTO.title(), taskDTO.description()));

        return true;
    }

    public boolean updateTask(Long id, UpdateTaskDTO taskDTO) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));

        if (!task.getTitle().equals(taskDTO.title())) {
            task.setTitle(taskDTO.title());
        }

        if (!task.getDescription().equals(taskDTO.description())) {
            task.setDescription(taskDTO.description());
        }

        if (!task.getCompleted().equals(taskDTO.completed())) {
            task.setCompleted(taskDTO.completed());
        }

        if (task.getCompleted()) {
            task.setCompletedAt(LocalDateTime.now());
        } else
            task.setCompletedAt(null);

        taskRepository.save(task);
        return true;
    }

    public Boolean deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new ResourceNotFoundException("Task not found with id: " + id);
        }
        taskRepository.deleteById(id);
        return true;
    }
}
