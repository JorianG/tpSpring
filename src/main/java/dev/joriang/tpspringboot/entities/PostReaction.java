package dev.joriang.tpspringboot.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table(name = "post_reaction")
@Getter
@Setter
public class PostReaction {
    @EmbeddedId
    private PostReactionId id = new PostReactionId();

    @ManyToOne
    @MapsId("postId")
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @MapsId("username")
    @JoinColumn(name = "user_username")
    private User user;

    @Column(nullable = false)
    private boolean isLike;  // true pour like, false pour dislike

    @CreationTimestamp
    private Date createdAt;
} 