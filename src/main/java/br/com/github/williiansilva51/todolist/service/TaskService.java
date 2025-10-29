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

    private User getUserAuth() throws ResourceNotFoundException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
    }

    private Task getTaskByUser(Long id) throws ResourceNotFoundException, SecurityException {
        User user = getUserAuth();

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));

        if (!task.getUser().getId().equals(user.getId())) {
            throw new SecurityException("User not authorized to update this task");
        }

        return task;
    }

    public TaskDTO getTaskById(Long id) throws ResourceNotFoundException {
        Task task = getTaskByUser(id);
        return taskToTaskDTO(task);
    }

    public List<TaskDTO> findTasksByName(String name) {
        User user = getUserAuth();

        return taskRepository.findByTitleAndUser_Id(name, user.getId()).stream()
                .map(this::taskToTaskDTO).toList();
    }

    public List<TaskDTO> getAllTasks() throws ResourceNotFoundException {
        User user = getUserAuth();

        return taskRepository.findByUser_Id(user.getId())
                .stream()
                .map(this::taskToTaskDTO)
                .toList();
    }

    public Task createTask(CreateTaskDTO taskDTO) throws IllegalArgumentException {
        User user = getUserAuth();

        if (taskRepository.findByTitleAndUser_Id(taskDTO.title(), user.getId()).isPresent()) {
            throw new IllegalArgumentException("User already has a task with title: " + taskDTO.title());
        }

        Task newTask = Task.builder().title(taskDTO.title())
                .description(taskDTO.description())
                .user(user)
                .createdAt(LocalDateTime.now())
                .build();

        return taskRepository.save(newTask);
    }

    public Task updateTask(Long id, UpdateTaskDTO taskDTO) throws ResourceNotFoundException, SecurityException {
        Task task = getTaskByUser(id);

        if (!task.getTitle().equals(taskDTO.title())) {
            task.setTitle(taskDTO.title());
        }

        if (!task.getDescription().equals(taskDTO.description())) {
            task.setDescription(taskDTO.description());
        }

        if (!task.getCompleted().equals(taskDTO.completed())) {
            task.setCompleted(taskDTO.completed());
        }

        if (!task.getCompleted())
            task.setCompletedAt(null);

        return taskRepository.save(task);
    }

    public Task completeTask(Long id) throws ResourceNotFoundException, SecurityException {
        Task task = getTaskByUser(id);
        task.setCompleted(true);
        task.setCompletedAt(LocalDateTime.now());

        return taskRepository.save(task);
    }

    public Boolean deleteTask(Long id) throws ResourceNotFoundException, SecurityException {
        User user = getUserAuth();

        Task task = taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));

        if (!task.getUser().getId().equals(user.getId())) {
            throw new SecurityException("User not authorized to delete this task");
        }

        taskRepository.deleteById(id);
        return true;
    }
}
