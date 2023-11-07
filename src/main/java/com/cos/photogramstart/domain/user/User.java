package com.cos.photogramstart.domain.user;

import com.cos.photogramstart.domain.image.Image;
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
//@ToString(exclude = "images")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String password;
    private String nickname;
    private String email;
    private String website;
    private String bio;
    private String profileImageUrl;


    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Image> images;

    private LocalDateTime createDate;

    @PrePersist
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }


}
