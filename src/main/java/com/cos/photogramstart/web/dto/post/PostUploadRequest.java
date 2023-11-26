package com.cos.photogramstart.web.dto.post;

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
