package dev.joriang.tpspringboot.services;

import dev.joriang.tpspringboot.entities.Post;
import dev.joriang.tpspringboot.entities.PostReaction;
import dev.joriang.tpspringboot.entities.PostReactionId;
import dev.joriang.tpspringboot.entities.User;
import dev.joriang.tpspringboot.repositories.PostReactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PostReactionService {
    @Autowired
    private PostReactionRepository reactionRepository;
    
    @Autowired
    private PostService postService;
    
    @Autowired
    private UserService userService;

    public PostReaction reactToPost(UUID postId, String username, boolean isLike) {
        Post post = postService.readPost(postId);
        User user = userService.readUser(username);

        // Vérifier si une réaction existe déjà
        Optional<PostReaction> existingReaction = reactionRepository
            .findByPostIdAndUserUsername(postId, username);

        if (existingReaction.isPresent()) {
            PostReaction reaction = existingReaction.get();
            reaction.setLike(isLike);
            return reactionRepository.save(reaction);
        }

        // Créer une nouvelle réaction
        PostReaction reaction = new PostReaction();
        reaction.setPost(post);
        reaction.setUser(user);
        reaction.setLike(isLike);
        
        return reactionRepository.save(reaction);
    }

    public boolean removeReaction(UUID postId, String username) {
        PostReactionId id = new PostReactionId(postId, username);
        reactionRepository.deleteById(id);
        return reactionRepository.existsById(id);
    }

    public List<PostReaction> getPostReactions(UUID postId) {
        return reactionRepository.findByPostId(postId);
    }
} 