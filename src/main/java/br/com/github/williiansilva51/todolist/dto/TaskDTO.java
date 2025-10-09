package br.com.github.williiansilva51.todolist.dto;

import java.time.LocalDateTime;

public record TaskDTO(String title, String description, Boolean completed, LocalDateTime createdAt,
                      LocalDateTime completedAt) {
}
