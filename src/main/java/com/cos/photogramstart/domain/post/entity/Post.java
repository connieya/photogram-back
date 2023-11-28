package com.cos.photogramstart.domain.post.entity;

import com.cos.photogramstart.domain.comment.entity.Comment;
import com.cos.photogramstart.domain.likes.entity.PostLike;
import com.cos.photogramstart.domain.user.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "posts")
@EntityListeners(AuditingEntityListener.class)
public class Post { // N : 1
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private int id;

    @Lob
    @Column(name = "post_content")
    private String caption;
    private String location;
    private String baseUrl;
    private String postImageUrl; // 사진을 전송받아서 사진을 서버에 특정 폴더에 저장

    @JoinColumn(name = "userId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "post") // 연관관계의 주인이 아니다.
    private List<PostLike> postLikes;


    @OneToOne
    @JoinColumn(name = "post_image_id")
    private PostImage postImage;

    @CreatedDate
    @Column(name = "post_upload_date")
    private LocalDateTime createDate;

    @OneToMany(mappedBy = "image")
    private List<Comment> comments = new ArrayList<>();

    @PrePersist // 디비에 INSERT 되기 직전에 실행
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }

}
