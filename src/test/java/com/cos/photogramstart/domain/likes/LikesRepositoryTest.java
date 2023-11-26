package com.cos.photogramstart.domain.likes;

import com.cos.photogramstart.service.PostLikeService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional

class LikesRepositoryTest {

    @Autowired
    private PostLIkeRepository likesRepository;

    @Autowired
    PostLikeService likesService;


    @Test
    public void Test() {
        //given
        int imageId = 7;
        int userId = 2;
        //when
        PostLike likes = likesRepository.findByImageIdAndUserId(imageId, userId);
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