package com.cos.photogramstart.domain.post.domain;

import com.cos.photogramstart.domain.comment.domain.Comment;
import com.cos.photogramstart.domain.post.application.PostUpload;
import com.cos.photogramstart.domain.user.domain.User;
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
    private Long id;
    @Lob
    @Column(name = "post_content")
    private String caption;
    private String location;

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

    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

    @Builder
    private Post(String caption, String location, User user, PostImage postImage) {
        this.caption = caption;
        this.location = location;
        this.user = user;
        this.postImage = postImage;
    }

    public static Post create(PostUpload postUpload, User user , PostImage postImage) {
        return Post
                .builder()
                .caption(postUpload.getCaption())
                .location(postUpload.getLocation())
                .user(user)
                .postImage(postImage)
                .build();
    }

    public static Post create(Long id , String caption ,String location, User user ,PostImage postImage){
        return Post
                .builder()
                .id(id)
                .caption(caption)
                .location(location)
                .user(user)
                .postImage(postImage)
                .build();
    }

    @PrePersist // 디비에 INSERT 되기 직전에 실행
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }

}
