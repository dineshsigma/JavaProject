package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    // ✅ CREATE
    public User saveUser(User user) {
        return repository.save(user);
    }

    // ✅ READ
    public List<User> getAllUsers() {
        return repository.findAll();
    }

    // ✅ GET BY ID
    public User getUserById(Long id) {
        Optional<User> user = repository.findById(id);
        return user.orElse(null);
    }

    // ✅ UPDATE
    public User updateUser(Long id, User userDetails) {
        return repository.findById(id).map(user -> {
            user.setName(userDetails.getName());
            user.setEmail(userDetails.getEmail());
            user.setPassword(userDetails.getPassword());
            return repository.save(user);
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }

    // ✅ DELETE
    public void deleteUser(Long id) {
        repository.deleteById(id);
    }
}