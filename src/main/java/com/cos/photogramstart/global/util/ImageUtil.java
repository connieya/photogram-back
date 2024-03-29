package com.cos.photogramstart.global.util;

import com.cos.photogramstart.domain.user.domain.Image;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public class ImageUtil {

    public static Image convertMultipartToImage(MultipartFile file , String uuid) {
        String originalFilename = file.getOriginalFilename();
        String name = FilenameUtils.getBaseName(originalFilename);
        assert FilenameUtils.getExtension(originalFilename) != null;
        String type = FilenameUtils.getExtension(originalFilename).toUpperCase();

        return Image.builder()
                .imageType(type)
                .imageUUID(uuid)
                .imageName(name)
                .build();


    }
}
