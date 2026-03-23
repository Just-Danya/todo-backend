package pal.comp.todobackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pal.comp.todobackend.entity.User;
import pal.comp.todobackend.repository.UserRepository;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getCurrentUser() {
        return userRepository.findByEmail("test@example.com")
                .orElseGet(() -> {
                    User testUser = new User();
                    testUser.setEmail("test@example.com");
                    testUser.setName("Test User");
                    testUser.setGoogleId("test123");
                    testUser.setPictureUrl("https://ui-avatars.com/api/?background=667eea&color=fff&name=Test+User");
                    return userRepository.save(testUser);
                });
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            User testUser = getCurrentUser();
            users = List.of(testUser);
        }
        return users;
    }
}