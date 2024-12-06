package dev.joriang.tpspringboot.controllers;

import dev.joriang.tpspringboot.entities.Post;
import dev.joriang.tpspringboot.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/posts")
public class PostController {
    @Autowired
    private PostService postService;

    @GetMapping
    public @ResponseBody ResponseEntity<Iterable<Post>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping("/{id}")
    public @ResponseBody ResponseEntity<Post> getPost(@PathVariable UUID id) {
        return ResponseEntity.ok(postService.readPost(id));
    }

    @GetMapping("/user/{username}")
    public @ResponseBody ResponseEntity<Iterable<Post>> getPostsByUser(@PathVariable String username) {
        return ResponseEntity.ok(postService.getPostsByUser(username));
    }

    @PostMapping("/user/{username}")
    public ResponseEntity<Post> createPost(
            @ModelAttribute Post post,
            @PathVariable String username) {
        return ResponseEntity.ok(postService.createPost(post, username));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Post> updatePost(
            @PathVariable UUID id,
            @ModelAttribute Post post) {
        post.setId(id);
        return ResponseEntity.ok(postService.updatePost(post));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable UUID id) {
        postService.deletePost(id);
        return ResponseEntity.ok().build();
    }
} 