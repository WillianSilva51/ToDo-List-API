package br.com.github.williiansilva51.todolist.dto.task;

import jakarta.validation.constraints.NotBlank;

public record CreateTaskDTO(@NotBlank String title, String description) {
}
