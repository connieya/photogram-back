package com.cos.photogramstart.domain.comment.fixture;

import com.cos.photogramstart.domain.comment.domain.Comment;

import static com.cos.photogramstart.domain.post.fixture.PostFixture.*;
import static com.cos.photogramstart.domain.user.fixture.UserFixture.*;

public class CommentFixture {

    public static final Comment COMMENT_1 = Comment.create(
            1,
            "댓글 달기 1",
            POST_1,
            CONY
    );

    public static final Comment COMMENT_2 = Comment.create(
            2,
            "댓글 달기 2",
            POST_1,
            HONG
    );
}
