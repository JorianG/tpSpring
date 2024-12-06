package dev.joriang.tpspringboot.controllers;

import dev.joriang.tpspringboot.entities.PostReaction;
import dev.joriang.tpspringboot.services.PostReactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/posts/{postId}/reactions")
public class PostReactionController {
    @Autowired
    private PostReactionService reactionService;

    @PostMapping("/user/{username}/like")
    public ResponseEntity<PostReaction> likePost(
            @PathVariable UUID postId,
            @PathVariable String username) {
        return ResponseEntity.ok(reactionService.reactToPost(postId, username, true));
    }

    @PostMapping("/user/{username}/dislike")
    public ResponseEntity<PostReaction> dislikePost(
            @PathVariable UUID postId,
            @PathVariable String username) {
        return ResponseEntity.ok(reactionService.reactToPost(postId, username, false));
    }

    @DeleteMapping("/user/{username}")
    public ResponseEntity<Void> removeReaction(
            @PathVariable UUID postId,
            @PathVariable String username) {
        reactionService.removeReaction(postId, username);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public @ResponseBody ResponseEntity<List<PostReaction>> getPostReactions(
            @PathVariable UUID postId) {
        return ResponseEntity.ok(reactionService.getPostReactions(postId));
    }
} 