package com.cos.photogramstart.domain.post.application;

import com.cos.photogramstart.global.util.AuthUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.cos.photogramstart.domain.user.fixture.UserFixture.PARK;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @InjectMocks
    private PostService postService;

    @Mock
    private AuthUtil authUtil;

//    @DisplayName("게시물을 등록한다. ")
//    @Test
//    void upload(){
//        // given
//        given(authUtil.getLoginUser())
//                .willReturn(PARK);
//        // when
//
//
//        //then
//    }

}