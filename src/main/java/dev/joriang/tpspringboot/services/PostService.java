package dev.joriang.tpspringboot.services;

import dev.joriang.tpspringboot.entities.Post;
import dev.joriang.tpspringboot.entities.User;
import dev.joriang.tpspringboot.repositories.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PostService implements PostServiceInterface {
    @Autowired
    private PostRepository postRepository;
    
    @Autowired
    private UserService userService;

    @Override
    public Post createPost(Post post, String username) {
        User user = userService.readUser(username);
        post.setUser(user);
        return postRepository.save(post);
    }

    @Override
    public Post readPost(UUID id) {
        return postRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Post not found"));
    }

    @Override
    public Post updatePost(Post post) {
        Post existingPost = postRepository.findById(post.getId())
            .orElseThrow(() -> new EntityNotFoundException("Post not found"));
            

        post.setUser(existingPost.getUser());
        
        return postRepository.save(post);
    }

    @Override
    public void deletePost(UUID id) {
        postRepository.deleteById(id);
    }

    @Override
    public Iterable<Post> getAllPosts() {
        Iterable<Post> posts = postRepository.findAll();
        
        return posts;
    }

    @Override
    public Iterable<Post> getPostsByUser(String username) {
        return postRepository.findByUserUsername(username);
    }
} 