package br.com.github.williiansilva51.todolist.dto.user;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record LoginUserDTO(@Valid @NotBlank String username, @Valid @NotBlank String password) {
}
