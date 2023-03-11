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

    @Query(value = "select i.* from image i inner join\n" +
            "(select imageId , count(imageId) likeCount from likes group by imageId) c\n" +
            "on i.id = c.imageId order by likeCount desc", nativeQuery = true)
    List<Image> mPopular();

}
