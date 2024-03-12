package com.cos.photogramstart.domain.folllow.infrastructure;

import com.cos.photogramstart.domain.folllow.domain.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow,Integer> , FollowCustomRepository {


    boolean existsByFromUserIdAndToUserId(Long fromUserId , Long toUserId);

    Optional<Follow> findByFromUserIdAndToUserId(Long fromUserId , Long toUserId);
}
