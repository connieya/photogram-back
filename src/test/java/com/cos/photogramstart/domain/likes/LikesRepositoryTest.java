package com.cos.photogramstart.domain.likes;

import com.cos.photogramstart.domain.post.infrastructure.PostLIkeRepository;
import com.cos.photogramstart.domain.post.application.PostLikeService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional

class LikesRepositoryTest {

    @Autowired
    private PostLIkeRepository postLIkeRepository;

    @Autowired
    PostLikeService postLikeService;


    @Test
    public void Test2() {
        //given
        int imageId = 7;
        int userId = 1;
        //when
        boolean likeState = postLikeService.likeState(imageId, userId);
        Assertions.assertThat(likeState).isEqualTo(true);

    }
}