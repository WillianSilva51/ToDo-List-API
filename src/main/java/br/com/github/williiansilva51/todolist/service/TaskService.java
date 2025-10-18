package br.com.github.williiansilva51.todolist.service;

import br.com.github.williiansilva51.todolist.dto.task.CreateTaskDTO;
import br.com.github.williiansilva51.todolist.dto.task.TaskDTO;
import br.com.github.williiansilva51.todolist.dto.task.UpdateTaskDTO;
import br.com.github.williiansilva51.todolist.entity.Task;
import br.com.github.williiansilva51.todolist.entity.User;
import br.com.github.williiansilva51.todolist.handler.ResourceNotFoundException;
import br.com.github.williiansilva51.todolist.repository.TaskRepository;
import br.com.github.williiansilva51.todolist.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskDTO taskToTaskDTO(Task task) {
        return new TaskDTO(task.getTitle(), task.getDescription(), task.getCompleted(), task.getCreatedAt(), task.getCompletedAt());
    }

    public TaskDTO getTaskById(Long id) throws ResourceNotFoundException {
        return taskRepository.findById(id)
                .map(this::taskToTaskDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
    }

    public List<TaskDTO> getAllTasks() throws ResourceNotFoundException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));

        return taskRepository.findByUser_Id(user.getId())
                .stream()
                .map(this::taskToTaskDTO)
                .toList();
    }

    public Task createTask(CreateTaskDTO taskDTO) throws IllegalArgumentException {
        if (taskRepository.findByTitle(taskDTO.title()).isPresent()) {
            throw new IllegalArgumentException("Task already exists with title: " + taskDTO.title());
        }

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));

        Task newTask = Task.builder().title(taskDTO.title()).description(taskDTO.description()).user(user).createdAt(LocalDateTime.now()).build();

        return taskRepository.save(newTask);
    }

    public Task updateTask(Long id, UpdateTaskDTO taskDTO) throws ResourceNotFoundException, SecurityException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));

        if (!task.getUser().getId().equals(user.getId())) {
            throw new SecurityException("User not authorized to update this task");
        }

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

        return taskRepository.save(task);
    }

    public Boolean deleteTask(Long id) throws ResourceNotFoundException, SecurityException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));

        Task task = taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));

        if (!task.getUser().getId().equals(user.getId())) {
            throw new SecurityException("User not authorized to delete this task");
        }

        taskRepository.deleteById(id);
        return true;
    }
}
