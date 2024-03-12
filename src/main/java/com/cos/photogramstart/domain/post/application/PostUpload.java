package com.cos.photogramstart.domain.post.application;

import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class PostUpload {

    private String caption;
    private String location;

}
