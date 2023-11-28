package com.cos.photogramstart.domain.user.entity;

import com.cos.photogramstart.domain.post.entity.Post;
import com.cos.photogramstart.global.common.Image;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String password;
    private String username;
    private String email;
    private String website;
    private String bio;


    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "imageUrl", column = @Column(name = "profile_image_url")),
            @AttributeOverride(name = "imageType", column = @Column(name = "profile_image_type")),
            @AttributeOverride(name = "imageUUID", column = @Column(name = "profile_image_uuid")),
            @AttributeOverride(name = "imageName", column = @Column(name = "profile_image_name")),
    })
    private Image image;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Post> images;

    private LocalDateTime createDate;

    @PrePersist
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }


    public void deleteImage() {
        this.image = Image.builder()
                .imageName("base")
                .imageType("PNG")
                .imageUrl("")
                .imageUUID("base-UUID")
                .build();

    }

}
