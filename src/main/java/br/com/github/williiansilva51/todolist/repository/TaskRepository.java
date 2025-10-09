package br.com.github.williiansilva51.todolist.repository;

import br.com.github.williiansilva51.todolist.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
