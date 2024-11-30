package dev.joriang.tpspringboot.controllers;

import dev.joriang.tpspringboot.entities.User;
import dev.joriang.tpspringboot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/user")

public class UserController {


    @Autowired
    private UserService userService;


    @GetMapping("/all")
    public @ResponseBody ResponseEntity<Iterable<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{username}")
    public @ResponseBody ResponseEntity<User> getUser(@PathVariable String username) {
        User user = userService.readUser(username);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/add") 
    public ResponseEntity<User> createUser(
            @ModelAttribute User user,
            @RequestParam String password) {
        user.setPasswordHash(password);
        userService.createUser(user);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<User> deleteUser(@PathVariable String username) {
        User user = userService.readUser(username);
        userService.deleteUser(username);
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/{username}")
    public ResponseEntity<User> updateUser(
            @PathVariable String username,
            @ModelAttribute User user) {
        user.setUsername(username);
        userService.updateUser(user);
        return ResponseEntity.ok(user);
    }




}
