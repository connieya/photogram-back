package com.cos.photogramstart.domain.likes;

import com.cos.photogramstart.service.LikesService;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional

class LikesRepositoryTest {

    @Autowired
    private LikesRepository likesRepository;

    @Autowired
    LikesService likesService;


    @Test
    public void Test() {
        //given
        int imageId = 7;
        int userId = 2;
        //when
        Likes likes = likesRepository.findByImageIdAndUserId(imageId, userId);
        //then
        Assertions.assertThat(likes).isNull();

        System.out.println("likes = " + likes);
    }

    @Test
    public void Test2() {
        //given
        int imageId = 7;
        int userId = 1;
        //when
        boolean likeState = likesService.likeState(imageId, userId);
        Assertions.assertThat(likeState).isEqualTo(true);

    }
}