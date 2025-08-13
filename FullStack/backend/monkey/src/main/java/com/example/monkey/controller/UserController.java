package com.example.monkey.controller;

import com.example.monkey.entity.User;
import com.example.monkey.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"}, allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
public class UserController {
    
    private final UserRepository userRepository;
    
    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    @GetMapping("/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        return userRepository.findByUsername(username)
                .map(user -> ResponseEntity.ok(user))
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(userRepository.save(user));
    }
    
    @PutMapping("/{username}/points")
    public ResponseEntity<User> updateUserPoints(@PathVariable String username, @RequestParam Integer points) {
        return userRepository.findByUsername(username)
                .map(user -> {
                    user.setPoints(points);
                    return ResponseEntity.ok(userRepository.save(user));
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PutMapping("/{username}/add-points")
    public ResponseEntity<User> addPointsToUser(@PathVariable String username, @RequestParam Integer points) {
        return userRepository.findByUsername(username)
                .map(user -> {
                    user.setPoints(user.getPoints() + points);
                    return ResponseEntity.ok(userRepository.save(user));
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable String username) {
        return userRepository.findByUsername(username)
                .map(user -> {
                    userRepository.delete(user);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}