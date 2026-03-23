package pal.comp.todobackend.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pal.comp.todobackend.dto.CreateTodoRequest;
import pal.comp.todobackend.dto.TodoResponse;
import pal.comp.todobackend.entity.Priority;
import pal.comp.todobackend.entity.TaskComment;
import pal.comp.todobackend.service.TodoService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/todos")
@CrossOrigin(origins = "*")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @PostMapping
    public ResponseEntity<TodoResponse> createTodo(@Valid @RequestBody CreateTodoRequest request) {
        TodoResponse response = todoService.createTodo(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TodoResponse>> getAllTodos() {
        List<TodoResponse> todos = todoService.getAllTodos();
        return ResponseEntity.ok(todos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoResponse> getTodoById(@PathVariable Long id) {
        TodoResponse todo = todoService.getTodoById(id);
        return ResponseEntity.ok(todo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoResponse> updateTodo(@PathVariable Long id, @Valid @RequestBody CreateTodoRequest request) {
        TodoResponse updatedTodo = todoService.updateTodo(id, request);
        return ResponseEntity.ok(updatedTodo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<TodoResponse> markAsCompleted(@PathVariable Long id) {
        TodoResponse completedTodo = todoService.markAsCompleted(id);
        return ResponseEntity.ok(completedTodo);
    }

    @GetMapping("/completed")
    public ResponseEntity<List<TodoResponse>> getCompletedTodos() {
        List<TodoResponse> todos = todoService.getCompletedTodos();
        return ResponseEntity.ok(todos);
    }

    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<TodoResponse>> getTodosByPriority(@PathVariable Priority priority) {
        List<TodoResponse> todos = todoService.getTodosByPriority(priority);
        return ResponseEntity.ok(todos);
    }

    @PatchMapping("/{id}/assign")
    public ResponseEntity<TodoResponse> assignTodo(@PathVariable Long id, @RequestBody Map<String, Long> request) {
        Long userId = request.get("userId");
        TodoResponse todo = todoService.assignTodo(id, userId);
        return ResponseEntity.ok(todo);
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<TaskComment> addComment(@PathVariable Long id, @RequestBody Map<String, String> request) {
        TaskComment comment = todoService.addComment(id, request.get("content"));
        return ResponseEntity.ok(comment);
    }
}