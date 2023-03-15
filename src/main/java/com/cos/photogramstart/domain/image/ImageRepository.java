package com.cos.photogramstart.domain.image;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ImageRepository extends JpaRepository<Image , Integer> , ImageRepositoryCustom {

    @Query(value = "SELECT * FROM image WHERE userId in \n" +
            "(SELECT toUserId FROM follow WHERE fromUserId = :principalId ) ORDER BY id DESC" , nativeQuery = true)
    Page<Image> mStory(int principalId, Pageable pageable);
}
