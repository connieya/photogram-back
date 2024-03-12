package com.cos.photogramstart.domain.post.fixture;

import com.cos.photogramstart.domain.post.domain.Post;
import com.cos.photogramstart.domain.post.domain.PostImage;
import com.cos.photogramstart.domain.user.fixture.UserFixture;

public class PostFixture {

    public static final Post POST_1 = Post.create(
            1L
            ,"게시물 1"
            ,"서울시 송파구"
            , UserFixture.PARK
            ,new PostImage()
    );
}
