package dev.joriang.tpspringboot.repositories;

import dev.joriang.tpspringboot.entities.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface PostRepository extends CrudRepository<Post, UUID> {
    Iterable<Post> findByUserUsername(String username);
} 