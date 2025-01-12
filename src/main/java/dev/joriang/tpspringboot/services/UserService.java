package dev.joriang.tpspringboot.services;

import dev.joriang.tpspringboot.entities.User;
import dev.joriang.tpspringboot.exceptions.UserAlreadyExistsException;
import dev.joriang.tpspringboot.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;

import lombok.extern.flogger.Flogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@Slf4j
@Service
public class UserService implements UserSeviceInterface, UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    @Override
    public User createUser(User user) {
        if (userRepository.existsById(user.getUsername())) {
            throw new UserAlreadyExistsException(user.getUsername());
        }

        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        return userRepository.save(user);
    }

    @Override
    public User readUser(String username) {
        return userRepository.findById(username)
            .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    @Override
    public void updateUser(User user) {
        User existingUser = userRepository.findById(user.getUsername())
            .orElseThrow(() -> new EntityNotFoundException("User not found"));
        
        user.setCreatedAt(existingUser.getCreatedAt());
        
        if (user.getPasswordHash() == null || user.getPasswordHash().isEmpty()) {
            user.setPasswordHash(existingUser.getPasswordHash());
        } else {
            user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        }
        
        userRepository.save(user);
    }

    @Override
    public void deleteUser(String username) {
        userRepository.deleteById(username);
    }

    @Override
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = readUser(username);
        return org.springframework.security.core.userdetails.User
            .withUsername(user.getUsername())
            .password(user.getPasswordHash())
            .authorities(new ArrayList<>())
            .build();
    }
}
