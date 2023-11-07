package com.cos.photogramstart.web.dto.image;

import lombok.Getter;

@Getter
public class UserImageResponse {

    private String postImageUrl;
    private String caption;
    private int imageId;
    private long likeCount;
}
