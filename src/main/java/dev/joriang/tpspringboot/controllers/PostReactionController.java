package dev.joriang.tpspringboot.controllers;

import dev.joriang.tpspringboot.entities.PostReaction;
import dev.joriang.tpspringboot.services.PostReactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/reactions")
public class PostReactionController {
    @Autowired
    private PostReactionService reactionService;

    @PostMapping("/posts/{postId}/user/{username}/like")
    @PreAuthorize("hasRole('PUBLISHER') or hasRole('MODERATOR')")
    public ResponseEntity<PostReaction> likePost(
            @PathVariable UUID postId,
            @PathVariable String username) {
        return ResponseEntity.ok(reactionService.reactToPost(postId, username, true));
    }

    @PostMapping("/posts/{postId}/user/{username}/dislike")
    @PreAuthorize("hasRole('PUBLISHER') or hasRole('MODERATOR')")
    public ResponseEntity<PostReaction> dislikePost(
            @PathVariable UUID postId,
            @PathVariable String username) {
        return ResponseEntity.ok(reactionService.reactToPost(postId, username, false));
    }

    @DeleteMapping("/posts/{postId}/user/{username}")
    @PreAuthorize("hasRole('PUBLISHER') or hasRole('MODERATOR')")
    public ResponseEntity<List<PostReaction>> removeReaction(
            @PathVariable UUID postId,
            @PathVariable String username) {
        reactionService.removeReaction(postId, username);
        return ResponseEntity.ok(reactionService.getPostReactions(postId));
    }

    @GetMapping("/posts/{postId}")
    @PreAuthorize("hasRole('PUBLISHER') or hasRole('MODERATOR') or isAnonymous()")
    public ResponseEntity<List<PostReaction>> getPostReactions(
            @PathVariable UUID postId) {
        return ResponseEntity.ok(reactionService.getPostReactions(postId));
    }
} 