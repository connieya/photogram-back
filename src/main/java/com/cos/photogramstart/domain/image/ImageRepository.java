package com.cos.photogramstart.domain.image;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image , Integer> {

    @Query(value = "SELECT * FROM image WHERE userId in \n" +
            "(SELECT toUserId FROM subscribe WHERE fromUserId = :principalId);" , nativeQuery = true)
    List<Image> mStory(int principalId, Pageable pageable);
}
