package com.cos.photogramstart.domain.user.domain;

import com.cos.photogramstart.domain.post.domain.Post;
import com.cos.photogramstart.domain.user.application.command.SignUpCommand;
import com.cos.photogramstart.domain.user.application.command.UserUpdateCommand;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "member")
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
    private List<Post> posts;

    private LocalDateTime createDate;

    @PrePersist
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }


    @Builder
    private User(Long id, String name, String password, String username, String email, String website, String bio, Image image, List<Post> images, LocalDateTime createDate) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.username = username;
        this.email = email;
        this.website = website;
        this.bio = bio;
        this.image = image;
        this.posts = images;
        this.createDate = createDate;
    }

    public void deleteImage() {
        this.image = Image.builder()
                .imageName("base")
                .imageType("PNG")
                .imageUrl("")
                .imageUUID("base-UUID")
                .build();

    }

    public static User create(SignUpCommand signUpCommand) {
        return User
                .builder()
                .username(signUpCommand.getUsername())
                .password(signUpCommand.getPassword())
                .email(signUpCommand.getEmail())
                .name(signUpCommand.getName())
                .image(Image.init())
                .build();
    }

    public static User create(Long id, String username, String password, String name, String email) {
        return User
                .builder()
                .id(id)
                .username(username)
                .password(password)
                .name(name)
                .email(email)
                .build();
    }

    public User update(UserUpdateCommand userUpdateCommand) {
        this.name = userUpdateCommand.getName();
        this.username = userUpdateCommand.getUsername();
        this.website = userUpdateCommand.getWebsite();
        this.bio = userUpdateCommand.getBio();
        return this;
    }
}
