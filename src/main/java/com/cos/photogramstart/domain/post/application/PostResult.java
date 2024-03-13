package com.cos.photogramstart.domain.post.application;


import lombok.Getter;
import lombok.NoArgsConstructor;



@Getter
@NoArgsConstructor
public class PostResult {

    private Long postId;
    private String postImageUrl;
    private String content;
    private Long userId;
    private String username;
    private String profileImageUrl;
    private boolean likeState;
    private int likeCount;
}
