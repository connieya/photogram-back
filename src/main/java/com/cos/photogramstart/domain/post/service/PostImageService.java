package com.cos.photogramstart.domain.post.service;

import com.cos.photogramstart.domain.post.repository.PostImageRepository;
import com.cos.photogramstart.global.aws.S3Uploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostImageService {

    private final PostImageRepository postImageRepository;
    private final S3Uploader s3Uploader;

    public void uploadImage(MultipartFile file) {
        s3Uploader.uploadImage(file,"member");
    }
}
