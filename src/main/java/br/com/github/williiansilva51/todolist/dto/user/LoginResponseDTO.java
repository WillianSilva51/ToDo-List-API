package br.com.github.williiansilva51.todolist.dto.user;

import jakarta.validation.constraints.NotBlank;

public record LoginResponseDTO(@NotBlank String token) {
}