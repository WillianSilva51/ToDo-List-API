package br.com.github.williiansilva51.todolist.service;

import br.com.github.williiansilva51.todolist.dto.TaskDTO;
import br.com.github.williiansilva51.todolist.entity.Task;
import br.com.github.williiansilva51.todolist.repository.TaskRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.stereotype.Service;

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

    public boolean createTask(TaskDTO taskDTO) {
        taskRepository.save(new Task(taskDTO.title(), taskDTO.description()));

        return true;
    }
}
