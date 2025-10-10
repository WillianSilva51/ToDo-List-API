package br.com.github.williiansilva51.todolist.dto.user;

import jakarta.validation.constraints.NotBlank;

public record UserDTO(@NotBlank String username, String email) {
}
