package pal.comp.todobackend.dto;

import jakarta.validation.constraints.NotBlank;
import pal.comp.todobackend.entity.Priority;
import java.time.LocalDateTime;

public class CreateTodoRequest {

    @NotBlank(message = "Title is required")
    private String title;

    private String description;
    private Priority priority = Priority.MEDIUM;
    private LocalDateTime dueDate;
    private Long assignedToId;  // ← Это поле должно быть

    // Getters and Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Priority getPriority() { return priority; }
    public void setPriority(Priority priority) { this.priority = priority; }

    public LocalDateTime getDueDate() { return dueDate; }
    public void setDueDate(LocalDateTime dueDate) { this.dueDate = dueDate; }

    public Long getAssignedToId() { return assignedToId; }
    public void setAssignedToId(Long assignedToId) { this.assignedToId = assignedToId; }
}