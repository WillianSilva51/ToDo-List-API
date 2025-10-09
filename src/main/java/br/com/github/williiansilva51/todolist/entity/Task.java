package br.com.github.williiansilva51.todolist.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    private Boolean completed = false;

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime completedAt;

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
