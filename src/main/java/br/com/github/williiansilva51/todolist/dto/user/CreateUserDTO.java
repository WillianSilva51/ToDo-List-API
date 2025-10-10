package br.com.github.williiansilva51.todolist.dto.user;

import jakarta.validation.constraints.NotBlank;

public record CreateUserDTO(@NotBlank String username, @NotBlank String email, @NotBlank String password) {
}
