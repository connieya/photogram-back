package com.cos.photogramstart.domain.folllow.repository;

import com.cos.photogramstart.domain.folllow.entity.Follow;
import com.cos.photogramstart.domain.folllow.repository.querydsl.FollowRepositoryQuerydsl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow,Integer> , FollowRepositoryQuerydsl {


    boolean existsByFromUserIdAndToUserId(Long fromUserId , Long toUserId);

    Optional<Follow> findByFromUserIdAndToUserId(Long fromUserId , Long toUserId);
}
