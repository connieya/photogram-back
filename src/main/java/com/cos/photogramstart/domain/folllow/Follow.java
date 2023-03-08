package com.cos.photogramstart.domain.folllow;

import com.cos.photogramstart.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "subscribe_uk",
                        columnNames = {"fromUserId" ,"toUserId"}
                )
        }
)
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JoinColumn(name = "fromUserId")
    @ManyToOne
    private User fromUser;

    @JoinColumn(name = "toUserId")
    @ManyToOne
    private User toUser;

    private LocalDateTime createDate;

    @PrePersist // 디비에 INSERT 되기 직전에 실행
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }
}
