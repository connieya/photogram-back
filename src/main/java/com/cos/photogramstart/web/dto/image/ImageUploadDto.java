package com.cos.photogramstart.web.dto.image;

import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.user.User;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ImageUploadDto {
    private MultipartFile file;
    private String caption;

    public Image toEntity(String postImgUrl , User user){
        return Image.builder()
                .caption(caption)
                .postImageUrl(postImgUrl)
                .user(user)
                .build();
    }
}
