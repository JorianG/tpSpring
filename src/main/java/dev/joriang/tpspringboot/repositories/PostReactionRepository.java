package dev.joriang.tpspringboot.repositories;

import dev.joriang.tpspringboot.entities.PostReaction;
import dev.joriang.tpspringboot.entities.PostReactionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostReactionRepository extends JpaRepository<PostReaction, PostReactionId> {
    List<PostReaction> findByPostId(UUID postId);
    Optional<PostReaction> findByPostIdAndUserUsername(UUID postId, String username);
}