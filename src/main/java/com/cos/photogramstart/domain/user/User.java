package com.cos.photogramstart.domain.user;

import com.cos.photogramstart.domain.image.Image;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length = 20 , unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    private String nickname;
    @Column(nullable = false)
    private String email;
    private String website;
    private String bio;
    private String phone;
    private String gender;

    private String profileImageUrl;
    private String role;

    // user를 select 를 할때 해당 userId 로 등록된 image를 가져온다.
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY) // 연관 관계의 주인이 아니다.
    private List<Image> images;

    private LocalDateTime createDate;

    @PrePersist // 디비에 INSERT 되기 직전에 실행
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }
}
