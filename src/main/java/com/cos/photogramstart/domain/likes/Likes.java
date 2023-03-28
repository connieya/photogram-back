package com.cos.photogramstart.domain.likes;

import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "likes_uk",
                        columnNames = {"imageId" ,"userId"}
                )
        }
)
@ToString(exclude = {"user" ,"image"})
public class Likes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JoinColumn(name = "imageId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Image image;

    @JoinColumn(name = "userId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;

    private LocalDateTime createDate;

    @PrePersist // 디비에 INSERT 되기 직전에 실행
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }

}
