package br.com.github.williiansilva51.todolist.repository;

import br.com.github.williiansilva51.todolist.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Task> findByTitle(String title);

    List<Task> findByUser_Id(Long userId);
}
