package br.com.github.williiansilva51.todolist.repository;

import br.com.github.williiansilva51.todolist.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
