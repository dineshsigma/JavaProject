package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;	
    }

    // ✅ CREATE
    @PostMapping
    public User createUser(@RequestBody User user) {
        return service.saveUser(user);
    }

    // ✅ READ ALL
    @GetMapping
    public List<User> getAllUsers() {
        return service.getAllUsers();
    }

    // ✅ READ BY ID
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return service.getUserById(id);
    }

    // ✅ UPDATE
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id,
                           @RequestBody User user) {
        return service.updateUser(id, user);
    }

    // ✅ DELETE
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        service.deleteUser(id);
        return "User deleted successfully";
    }
}