package dev.joriang.tpspringboot.services;

import dev.joriang.tpspringboot.entities.User;

public interface UserSeviceInterface {
    void createUser(User user);
    User readUser(String username);
    void updateUser(User user);
    void deleteUser(String username);
    Iterable<User> getAllUsers();

}


