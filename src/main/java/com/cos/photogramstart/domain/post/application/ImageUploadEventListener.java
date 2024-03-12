package com.cos.photogramstart.domain.post.application;

import com.cos.photogramstart.domain.user.domain.Image;
import com.cos.photogramstart.global.aws.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;

@RequiredArgsConstructor
public class ImageUploadEventListener {

    private final S3Uploader s3Uploader;

    @EventListener(ImageUploadEvent.class)
    public Image handle(final ImageUploadEvent event ){
        return s3Uploader.uploadImage(event.getFile(), event.getDirName());
    }
}
