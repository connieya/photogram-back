package com.cos.photogramstart.domain.post.fixture;

import com.cos.photogramstart.domain.comment.fixture.CommentFixture;
import com.cos.photogramstart.domain.post.domain.Post;
import com.cos.photogramstart.domain.post.domain.PostImage;
import com.cos.photogramstart.domain.user.domain.Image;
import com.cos.photogramstart.domain.user.fixture.UserFixture;

import java.util.List;

import static com.cos.photogramstart.domain.comment.fixture.CommentFixture.*;

public class PostFixture {

    public static final Post POST_1 = Post.create(
            1L
            ,"게시물 1"
            ,"서울시 송파구"
            , UserFixture.PARK
            ,new PostImage(Image.init())
            , List.of(COMMENT_1,COMMENT_2)
    );
}
