package pal.comp.todobackend.dto;

import pal.comp.todobackend.entity.TaskComment;
import java.time.LocalDateTime;

public class CommentResponse {
    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private UserResponse user;

    public CommentResponse(TaskComment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
        if (comment.getUser() != null) {
            this.user = new UserResponse(comment.getUser());
        }
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public UserResponse getUser() { return user; }
    public void setUser(UserResponse user) { this.user = user; }
}