package dev.joriang.tpspringboot.repositories;

import org.springframework.data.repository.CrudRepository;

import dev.joriang.tpspringboot.entities.User;

public interface UserRepository extends CrudRepository<User, String> {
}