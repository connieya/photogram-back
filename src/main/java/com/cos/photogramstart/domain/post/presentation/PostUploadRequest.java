package com.cos.photogramstart.domain.post.presentation;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class PostUploadRequest {


    private String caption;
    private String location;
    private MultipartFile file;
}
