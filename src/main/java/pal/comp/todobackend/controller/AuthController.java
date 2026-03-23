package pal.comp.todobackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pal.comp.todobackend.dto.UserResponse;
import pal.comp.todobackend.entity.User;
import pal.comp.todobackend.service.UserService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser() {
        try {
            User user = userService.getCurrentUser();
            return ResponseEntity.ok(new UserResponse(user));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(null);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        // Для простой версии без OAuth2 просто возвращаем успех
        return ResponseEntity.ok().build();
    }
}