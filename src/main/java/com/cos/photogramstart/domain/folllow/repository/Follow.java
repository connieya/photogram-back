package com.cos.photogramstart.domain.folllow.repository;

import com.cos.photogramstart.domain.user.domain.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter @Getter
@Entity
@Table(name = "follows")
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_id")
    private Long id;

    @JoinColumn(name = "from_user_Id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User fromUser;

    @JoinColumn(name = "to_user_Id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User toUser;

    private LocalDateTime createDate;

    public Follow(User fromUser, User toUser) {
        this.fromUser = fromUser;
        this.toUser = toUser;
    }

    @PrePersist // 디비에 INSERT 되기 직전에 실행
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }
}
