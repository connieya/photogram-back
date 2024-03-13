package com.cos.photogramstart.domain.comment.domain;

import com.cos.photogramstart.domain.post.domain.Post;
import com.cos.photogramstart.domain.user.domain.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;
    private LocalDateTime createDate;


    @Builder
    private Comment(int id ,String content, User user, Post post) {
        this.id = id;
        this.content = content;
        this.user = user;
        this.post = post;
    }

    @PrePersist
    public void createDate(){
        this.createDate = LocalDateTime.now();
    }

    public static Comment create(String content , Post post , User user){
        return Comment
                .builder()
                .content(content)
                .user(user)
                .post(post)
                .build();
    }

    public static Comment create(int id, String content , Post post , User user){
        return Comment
                .builder()
                .id(id)
                .content(content)
                .user(user)
                .post(post)
                .build();
    }
}
