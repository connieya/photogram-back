package com.cos.photogramstart.domain.post.application;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class ImageUploadEvent {

    private MultipartFile file;
    private String dirName;
}
