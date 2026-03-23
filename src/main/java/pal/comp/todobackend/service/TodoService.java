package pal.comp.todobackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pal.comp.todobackend.dto.CreateTodoRequest;
import pal.comp.todobackend.dto.TodoResponse;
import pal.comp.todobackend.entity.*;
import pal.comp.todobackend.repository.TaskCommentRepository;
import pal.comp.todobackend.repository.TodoRepository;
import pal.comp.todobackend.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskCommentRepository taskCommentRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public TodoResponse createTodo(CreateTodoRequest request) {
        User currentUser = userService.getCurrentUser();

        Todo todo = new Todo();
        todo.setTitle(request.getTitle());
        todo.setDescription(request.getDescription());
        todo.setPriority(request.getPriority());
        todo.setDueDate(request.getDueDate());
        todo.setCreatedBy(currentUser);

        if (request.getAssignedToId() != null) {
            User assignedUser = userRepository.findById(request.getAssignedToId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            todo.setAssignedTo(assignedUser);
        }

        Todo savedTodo = todoRepository.save(todo);
        return new TodoResponse(savedTodo);
    }

    @Transactional(readOnly = true)
    public List<TodoResponse> getAllTodos() {
        List<Todo> todos = todoRepository.findAll();
        return todos.stream()
                .map(TodoResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TodoResponse getTodoById(Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found with id: " + id));
        return new TodoResponse(todo);
    }

    @Transactional
    public TodoResponse updateTodo(Long id, CreateTodoRequest request) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found with id: " + id));

        todo.setTitle(request.getTitle());
        todo.setDescription(request.getDescription());
        todo.setPriority(request.getPriority());
        todo.setDueDate(request.getDueDate());

        Todo updatedTodo = todoRepository.save(todo);
        return new TodoResponse(updatedTodo);
    }

    @Transactional
    public void deleteTodo(Long id) {
        if (!todoRepository.existsById(id)) {
            throw new RuntimeException("Todo not found with id: " + id);
        }
        todoRepository.deleteById(id);
    }

    @Transactional
    public TodoResponse markAsCompleted(Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found with id: " + id));
        todo.setCompleted(true);
        Todo updatedTodo = todoRepository.save(todo);
        return new TodoResponse(updatedTodo);
    }

    @Transactional
    public TodoResponse assignTodo(Long todoId, Long userId) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new RuntimeException("Todo not found with id: " + todoId));

        if (userId == null) {
            todo.setAssignedTo(null);
        } else {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
            todo.setAssignedTo(user);
        }

        Todo updatedTodo = todoRepository.save(todo);
        return new TodoResponse(updatedTodo);
    }

    @Transactional
    public TaskComment addComment(Long todoId, String content) {
        User currentUser = userService.getCurrentUser();
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new RuntimeException("Todo not found with id: " + todoId));

        TaskComment comment = new TaskComment();
        comment.setTodo(todo);
        comment.setUser(currentUser);
        comment.setContent(content);

        return taskCommentRepository.save(comment);
    }

    @Transactional(readOnly = true)
    public List<TodoResponse> getCompletedTodos() {
        return todoRepository.findByCompleted(true)
                .stream()
                .map(TodoResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TodoResponse> getTodosByPriority(Priority priority) {
        return todoRepository.findByPriority(priority)
                .stream()
                .map(TodoResponse::new)
                .collect(Collectors.toList());
    }
}