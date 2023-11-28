package com.cos.photogramstart.global.util;

import com.cos.photogramstart.global.common.Image;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public class ImageUtil {

    public static Image convertMultipartToImage(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String name = FilenameUtils.getBaseName(originalFilename);
        assert FilenameUtils.getExtension(originalFilename) != null;
        String type = FilenameUtils.getExtension(originalFilename).toUpperCase();

        return Image.builder()
                .imageType(type)
                .imageUUID(UUID.randomUUID().toString())
                .imageName(name)
                .build();


    }
}
