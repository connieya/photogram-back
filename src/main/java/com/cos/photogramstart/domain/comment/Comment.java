package com.cos.photogramstart.domain.comment;

import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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


    @JoinColumn(name = "userId")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn(name = "imageId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Image image;
    private LocalDateTime createDate;

    @PrePersist
    public void createDate(){
        this.createDate = LocalDateTime.now();
    }

    public static Comment addComment(String content , Image image ,User user){
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setImage(image);
        comment.setUser(user);
        return comment;
    }
}
