package dev.joriang.tpspringboot.controllers;

import dev.joriang.tpspringboot.entities.Post;
import dev.joriang.tpspringboot.services.PostService;
import dev.joriang.tpspringboot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPost(@PathVariable UUID id, Authentication authentication) {
        String username = authentication != null ? authentication.getName() : null;
        Post post = postService.readPost(id);

        if (username == null) {
            // Non-authenticated users
            post.setUser(null);

            return ResponseEntity.ok(post);
        }

        if (authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_MODERATOR"))) {
            // Moderators
            return ResponseEntity.ok(post);
        }

        if (authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_PUBLISHER"))) {
            // Publishers
            post.setLikes(null);
            post.setDislikes(null);
            return ResponseEntity.ok(post);
        }

        // Default for authenticated users
        post.setUser(null);
        post.setLikes(null);
        post.setDislikes(null);
        return ResponseEntity.ok(post);
    }

    @PostMapping("/new")
    @PreAuthorize("hasRole('PUBLISHER')")
    public ResponseEntity<Post> createPost(@RequestBody Post post, Authentication authentication) {
        String username = authentication.getName();
        post.setUser(userService.readUser(username));
        return ResponseEntity.ok(postService.createPost(post, username));
    }

    @PutMapping("/{id}/edit")
    @PreAuthorize("hasRole('PUBLISHER')")
    public ResponseEntity<Post> editPost(@PathVariable UUID id, @RequestBody Post post, Authentication authentication) {
        String username = authentication.getName();
        Post existingPost = postService.readPost(id);

        if (!existingPost.getUser().getUsername().equals(username)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        post.setId(id);
        post.setUser(userService.readUser(username));
        return ResponseEntity.ok(postService.updatePost(post));
    }

    @DeleteMapping("/{id}/delete")
    @PreAuthorize("hasRole('MODERATOR') or (hasRole('PUBLISHER') and @postService.isAuthor(#id, authentication.name))")
    public ResponseEntity<Void> deletePost(@PathVariable UUID id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/my-posts")
    @PreAuthorize("hasRole('PUBLISHER')")
    public ResponseEntity<Iterable<Post>> getMyPosts(Authentication authentication) {
        String username = authentication.getName();
        return ResponseEntity.ok(postService.getPostsByUser(username));
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('PUBLISHER')")
    public ResponseEntity<Iterable<Post>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }
}
