package br.com.github.williiansilva51.todolist.service;

import br.com.github.williiansilva51.todolist.dto.task.CreateTaskDTO;
import br.com.github.williiansilva51.todolist.dto.task.TaskDTO;
import br.com.github.williiansilva51.todolist.dto.task.UpdateTaskDTO;
import br.com.github.williiansilva51.todolist.entity.Task;
import br.com.github.williiansilva51.todolist.repository.TaskRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Builder
@AllArgsConstructor
@Service
public class TaskService {
    private TaskRepository taskRepository;

    public Optional<TaskDTO> getTaskById(Long id) {
        return Optional.of(taskRepository.findById(id).map(task -> new TaskDTO(task.getTitle(), task.getDescription(), task.getCompleted(), task.getCreatedAt(), task.getCompletedAt()))).orElseThrow();
    }

    public List<TaskDTO> getAllTasks() {
        return taskRepository.findAll().stream().map(task -> new TaskDTO(task.getTitle(), task.getDescription(), task.getCompleted(), task.getCreatedAt(), task.getCompletedAt())).toList();
    }

    public boolean createTask(CreateTaskDTO taskDTO) {
        taskRepository.save(new Task(taskDTO.title(), taskDTO.description()));

        return true;
    }

    public boolean updateTask(UpdateTaskDTO taskDTO) {
        Task task = taskRepository.findByTitle(taskDTO.title()).orElseThrow();

        task.setDescription(taskDTO.description());
        task.setCompleted(taskDTO.completed());

        if (task.getCompleted()) {
            task.setCompletedAt(LocalDateTime.now());
        }

        taskRepository.save(task);
        return true;
    }

    public Boolean deleteTask(TaskDTO taskDTO) {
        Task task = taskRepository.findByTitle(taskDTO.title()).orElseThrow();

        taskRepository.delete(task);

        return true;
    }
}
