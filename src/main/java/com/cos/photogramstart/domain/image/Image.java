package com.cos.photogramstart.domain.image;

import com.cos.photogramstart.domain.comment.Comment;
import com.cos.photogramstart.domain.likes.Likes;
import com.cos.photogramstart.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
@Entity
@ToString(exclude = {"likes" ,"comments","user"})
public class Image { // N : 1
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String caption;
    private String postImageUrl; // 사진을 전송받아서 사진을 서버에 특정 폴더에 저장

    @JoinColumn(name = "userId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;

    @JsonIgnoreProperties({"image"})
    @OneToMany(mappedBy = "image") // 연관관계의 주인이 아니다.
    private List<Likes> likes;

    private LocalDateTime createDate;

    @JsonIgnoreProperties({"image"})
    @OneToMany(mappedBy = "image")
    private List<Comment> comments = new ArrayList<>();

    @PrePersist // 디비에 INSERT 되기 직전에 실행
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }

}
