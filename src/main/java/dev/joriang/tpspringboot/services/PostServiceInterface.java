package dev.joriang.tpspringboot.services;

import dev.joriang.tpspringboot.entities.Post;

import java.util.UUID;

public interface PostServiceInterface {
    Post createPost(Post post, String username);
    Post readPost(UUID id);
    Post updatePost(Post post);
    void deletePost(UUID id);
    Iterable<Post> getAllPosts();
    Iterable<Post> getPostsByUser(String username);
} 